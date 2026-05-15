# Exam Results System - Critical Data Integrity Remediation Plan
## Implementation Guide

### Status: Phase 1 Code Complete ✓

---

## Phase 1: Core Data Integrity (COMPLETED)

### ✓ 1. Added Optimistic Locking
**File:** `ExamResult.java`
```java
@Version
private Long version;
```
**Purpose:** Prevents race conditions when multiple users edit simultaneously
**How it works:** JPA throws `OptimisticLockingFailureException` if version mismatch

**Migration SQL:**
```sql
ALTER TABLE exam_results ADD COLUMN version BIGINT DEFAULT 0;
```

---

### ✓ 2. Added Score Input Validation
**File:** `ExamResult.java`
```java
@Min(value = 0, message = "Điểm chuyên cần không được âm")
@Max(value = 10, message = "Điểm chuyên cần không được vượt quá 10")
private Double attendanceScore;

// Same for testScore, midtermScore, finalScore
```
**Purpose:** Prevent invalid scores (negative, >10, null in wrong contexts)
**Usage:** Enable validation in controller with `@Valid @RequestBody ExamResult`

**Next Step:** Add `@Valid` to ExamResultController POST/PUT endpoints

---

### ✓ 3. Fixed Total Score Display Bug
**File:** `ExamResult.java` - `getTotalScore()`
```java
// OLD: returned 0.0 when all scores null
// NEW: returns null when all scores null
if (attendanceScore == null && testScore == null && 
    midtermScore == null && finalScore == null) {
    return null;
}
```
**Purpose:** Distinguish "no data" from "student got 0"
**Frontend impact:** Update template to show "-" when totalScore is null

---

### ✓ 4. Added Published/Finalized State
**File:** `ExamResult.java`
```java
@Column(name = "published_at")
private LocalDateTime publishedAt;

@Column(name = "published_by")
private UUID publishedBy;
```

**File:** `ScoreStatus.java`
```java
public static final String PUBLISHED = "PUBLISHED";

public static boolean canPublish(String status) {
    return APPROVED.equals(status);
}

public static boolean canBeModified(String status) {
    return DRAFT.equals(status) || SUBMITTED.equals(status);
}
```

**Migration SQL:**
```sql
ALTER TABLE exam_results ADD COLUMN published_at DATETIME NULL;
ALTER TABLE exam_results ADD COLUMN published_by CHAR(36) NULL;
```

---

### ✓ 5. Created Audit Log Infrastructure
**Files Created:**
- `ExamResultAuditLog.java` - Entity
- `ExamResultAuditLogRepository.java` - Repository

**Database:**
```sql
CREATE TABLE exam_result_audit_logs (
    id CHAR(36) PRIMARY KEY,
    exam_result_id CHAR(36) NOT NULL,
    changed_by CHAR(36) NOT NULL,
    changed_at DATETIME NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    old_value VARCHAR(255),
    new_value VARCHAR(255),
    change_reason VARCHAR(255),
    ip_address VARCHAR(45),
    INDEX idx_exam_result_id (exam_result_id),
    INDEX idx_changed_by (changed_by),
    INDEX idx_changed_at (changed_at)
);
```

---

## Phase 2: Implementation Steps (TODO - Week 1)

### Step 1: Database Migration
1. Run migration scripts:
   - `database/migrate_exam_results_add_locking.sql`
   - `database/create_exam_result_audit_log_table.sql`

2. Verify columns added:
```sql
DESCRIBE exam_results;
-- Should show: version, published_at, published_by
SELECT * FROM exam_result_audit_logs LIMIT 1;
-- Should be empty initially
```

---

### Step 2: Update ExamResultService
Add these methods:

```java
// Validate score before save
private void validateScores(ExamResult examResult) {
    if (examResult.getAttendanceScore() != null && 
        (examResult.getAttendanceScore() < 0 || examResult.getAttendanceScore() > 10)) {
        throw new IllegalArgumentException("Điểm chuyên cần phải từ 0-10");
    }
    // Validate other score components similarly
}

// Prevent post-published modifications
private void checkCanModify(ExamResult examResult) {
    if (ScoreStatus.isPublished(examResult.getStatus())) {
        throw new ScoreAlreadyPublishedException(
            "Không thể sửa điểm đã công bố. ID: " + examResult.getId());
    }
}

// Audit log helper
private void logChange(UUID examResultId, String fieldName, 
                      String oldValue, String newValue, 
                      UUID changedBy, String reason, String ipAddress) {
    ExamResultAuditLog log = new ExamResultAuditLog();
    log.setId(UUID.randomUUID());
    log.setExamResultId(examResultId);
    log.setFieldName(fieldName);
    log.setOldValue(oldValue);
    log.setNewValue(newValue);
    log.setChangedBy(changedBy);
    log.setChangeReason(reason);
    log.setIpAddress(ipAddress);
    log.setChangedAt(LocalDateTime.now());
    auditLogRepository.save(log);
}

// Add PUBLISH action
public ExamResult publishScore(UUID resultId, UUID adminId) {
    ExamResult result = findById(resultId)
        .orElseThrow(() -> new RuntimeException("Score not found"));
    
    if (!ScoreStatus.canPublish(result.getStatus())) {
        throw new RuntimeException("Can only publish APPROVED scores");
    }
    
    result.setStatus(ScoreStatus.PUBLISHED);
    result.setPublishedAt(LocalDateTime.now());
    result.setPublishedBy(adminId);
    return save(result);
}
```

---

### Step 3: Update ExamResultController
Add validation and new endpoints:

```java
// Add @Valid to update endpoints
@PostMapping("/{id}/admin-edit")
public ResponseEntity<ExamResult> adminEdit(
        @PathVariable UUID id, 
        @Valid @RequestBody AdminEditRequest request,
        Authentication auth) {
    // Get current user ID from Authentication
    UUID userId = (UUID) auth.getPrincipal();
    
    return examResultService.findById(id)
        .map(existing -> {
            // Check if score is published
            if (ScoreStatus.isPublished(existing.getStatus())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null); // Or return proper error response
            }
            // Validate new scores
            if (request.attendanceScore() != null && 
                (request.attendanceScore() < 0 || request.attendanceScore() > 10)) {
                return ResponseEntity.badRequest().build();
            }
            // ... apply changes
            return ResponseEntity.ok(examResultService.save(existing));
        })
        .orElse(ResponseEntity.notFound().build());
}

// New endpoint: Publish scores
@PostMapping("/{id}/publish")
public ResponseEntity<ExamResult> publishScore(
        @PathVariable UUID id,
        Authentication auth) {
    UUID adminId = (UUID) auth.getPrincipal();
    try {
        ExamResult result = examResultService.publishScore(id, adminId);
        return ResponseEntity.ok(result);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(null);
    }
}

// New endpoint: Get audit log
@GetMapping("/{id}/audit-log")
public List<ExamResultAuditLog> getAuditLog(@PathVariable UUID id) {
    return auditLogRepository.findByExamResultIdOrderByChangedAtDesc(id);
}
```

---

### Step 4: Update Frontend (exam-result.html)

1. **Hide PUBLISH button for non-APPROVED scores:**
```html
<button class="btn btn-info btn-sm" 
        onclick="publishScore('${r.id}')" 
        title="Công bố điểm" 
        ${r.status !== 'APPROVED' ? 'disabled' : ''}>
    <i class="fas fa-globe"></i> Công bố
</button>
```

2. **Show AUDIT LOG button:**
```html
<button class="btn btn-secondary btn-sm" 
        onclick="showAuditLog('${r.id}')" 
        title="Xem lịch sử thay đổi">
    <i class="fas fa-history"></i> Lịch sử
</button>
```

3. **Prevent edit of PUBLISHED scores:**
```html
<button class="btn btn-danger btn-sm me-1" 
        onclick="editResult('${r.id}')" 
        title="Xem chi tiết" 
        ${r.status === 'PUBLISHED' ? 'disabled' : ''}>
    <i class="fas fa-edit"></i>
</button>
```

4. **Add publishScore() function:**
```javascript
async function publishScore(id) {
    if (!confirm('Công bố điểm cho sinh viên?')) return;
    
    try {
        const res = await fetch(`${API_URL}/${id}/publish`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        });
        if (!res.ok) throw new Error('Publish failed');
        alert('Công bố thành công!');
        loadResults();
    } catch (e) {
        alert('Lỗi: ' + e.message);
    }
}
```

5. **Add showAuditLog() function:**
```javascript
async function showAuditLog(id) {
    try {
        const res = await fetch(`${API_URL}/${id}/audit-log`);
        const logs = await res.json();
        
        let html = '<div><h5>Lịch sử thay đổi</h5><table class="table table-sm">';
        logs.forEach(log => {
            html += `<tr>
                <td>${log.fieldName}</td>
                <td>${log.oldValue || '-'} → ${log.newValue || '-'}</td>
                <td>${log.changedBy}</td>
                <td>${new Date(log.changedAt).toLocaleString('vi-VN')}</td>
            </tr>`;
        });
        html += '</table></div>';
        
        // Show in modal or alert
        alert(html);
    } catch (e) {
        alert('Lỗi: ' + e.message);
    }
}
```

---

## Phase 3: Access Control (TODO - Week 2)

### Implement Permission Checks
```java
// In controller, check roles before allowing actions

@PostMapping("/{id}/approve")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ExamResult> approveScore(@PathVariable UUID id) {
    // Only admins can approve
    return ...;
}

@PostMapping("/{id}/submit")
@PreAuthorize("hasRole('TEACHER')")
public ResponseEntity<ExamResult> submitScore(@PathVariable UUID id) {
    // Only teachers can submit
    return ...;
}

@PostMapping("/{id}/publish")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ExamResult> publishScore(@PathVariable UUID id, Authentication auth) {
    // Only admins can publish
    return ...;
}
```

---

## Phase 4: Testing Checklist

### Functional Tests
- [ ] Create score → should start in DRAFT
- [ ] Submit score → should move to SUBMITTED
- [ ] Approve score → should move to APPROVED
- [ ] Publish score → should move to PUBLISHED
- [ ] Try edit published → should fail with 409 CONFLICT
- [ ] Concurrent edit attempt → should fail with version mismatch

### Data Integrity Tests
- [ ] Score > 10 → rejected
- [ ] Score < 0 → rejected
- [ ] Null score handling → getTotalScore() returns null (not 0)
- [ ] Audit log creates entries for each change
- [ ] Audit log shows old→new value, who changed, when

### UI Tests
- [ ] PUBLISH button hidden for non-APPROVED scores
- [ ] EDIT button disabled for PUBLISHED scores
- [ ] Error messages clear when conflict/validation fails
- [ ] Audit log modal shows all changes

---

## SQL Verification

After running migrations, verify:
```sql
-- Check version column exists
DESC exam_results;

-- Check audit table exists
SELECT COUNT(*) FROM exam_result_audit_logs;

-- Check indexes
SHOW INDEX FROM exam_results WHERE Key_name LIKE 'idx_exam%';

-- Check foreign keys
SHOW CREATE TABLE exam_result_audit_logs;
```

---

## Files Created/Modified

### Created
- [x] `ExamResultAuditLog.java`
- [x] `ExamResultAuditLogRepository.java`
- [x] `ScoreAlreadyPublishedException.java`
- [x] `migrate_exam_results_add_locking.sql`
- [x] `create_exam_result_audit_log_table.sql`

### Modified
- [x] `ExamResult.java` (added @Version, validation, published fields)
- [x] `ScoreStatus.java` (added PUBLISHED state, helper methods)
- [ ] `ExamResultService.java` (TODO: add validation, audit logging)
- [ ] `ExamResultController.java` (TODO: add @Valid, publish endpoint)
- [ ] `exam-result.html` (TODO: hide actions, add audit log UI)

---

## Critical Next Steps (Do First!)

1. **Run database migrations** → Add version, published, audit_logs table
2. **Deploy new entity classes** → ExamResultAuditLog, repositories
3. **Add @Valid validation** → Prevent bad score data
4. **Add state machine checks** → Block post-published edits
5. **Test concurrent edit** → Verify OptimisticLocking works

---

## Risk Assessment

| Risk | Before | After | Mitigated By |
|------|--------|-------|--------------|
| Concurrent overwrites | 🔴 HIGH | 🟢 LOW | @Version optimistic locking |
| Invalid scores | 🔴 HIGH | 🟢 LOW | @Min/@Max validation + backend checks |
| Post-published edits | 🔴 HIGH | 🟢 LOW | Status machine validation |
| No change tracking | 🔴 HIGH | 🟢 LOW | ExamResultAuditLog table |
| Ambiguous 0 scores | 🟡 MED | 🟢 LOW | getTotalScore() returns null |
| No PUBLISHED state | 🔴 HIGH | 🟢 LOW | PUBLISHED state added |

---

## Success Metrics

✓ All critical Phase 1 code complete
- [ ] Phase 2: Validation + Audit logging working (Week 1)
- [ ] Phase 3: Permission checks enforced (Week 1)
- [ ] Phase 4: All tests passing (Week 2)
