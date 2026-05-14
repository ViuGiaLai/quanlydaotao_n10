package com.example.demo.n1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.n1.model.entity.UserRole;
import com.example.demo.n1.repository.RoleRepository;
import com.example.demo.n1.repository.UserRepository;
import com.example.demo.n1.repository.UserRoleRepository;
import com.example.demo.n2.model.entity.Student;
import com.example.demo.n2.model.entity.Teacher;
import com.example.demo.n2.repository.StudentRepository;
import com.example.demo.n2.repository.TeacherRepository;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public UserRoleService(UserRoleRepository userRoleRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional(readOnly = true)
    public List<UserRole> findAllActive() {
        return userRoleRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    @Transactional
    public UserRole create(UUID userId, UUID roleId, Boolean isActive) {
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User không tồn tại"));
        var role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Vai trò không tồn tại"));

        // Check if already assigned
        Optional<UserRole> existing = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
        if (existing.isPresent()) {
            throw new RuntimeException("User này đã được gán vai trò này rồi");
        }

        UserRole ur = new UserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        ur.setIsActive(isActive != null ? isActive : Boolean.TRUE);
        ur.setCreatedAt(LocalDateTime.now());
        ur.setUpdatedAt(LocalDateTime.now());
        userRoleRepository.save(ur);

        // Auto create profile based on role code
        createProfileIfNeeded(user, role);

        return ur;
    }

    private void createProfileIfNeeded(com.example.demo.n1.model.entity.User user, com.example.demo.n1.model.entity.Role role) {
        String roleCode = role.getCode();
        UUID userId = user.getId();

        if ("LECTURER".equalsIgnoreCase(roleCode) || "TEACHER".equalsIgnoreCase(roleCode)) {
            // Create teacher profile if not exists
            if (teacherRepository.findByUser_Id(userId).isEmpty()) {
                Teacher teacher = new Teacher();
                teacher.setUserId(userId);
                teacher.setCode(user.getUsername());
                teacher.setFullname(user.getUsername());
                teacher.setEmail(user.getEmail());
                teacher.setPhone(user.getPhone());
                teacher.setIsActive(true);
                teacher.setCreatedAt(LocalDateTime.now());
                teacher.setUpdatedAt(LocalDateTime.now());
                teacherRepository.save(teacher);
            }
        } else if ("STUDENT".equalsIgnoreCase(roleCode)) {
            // Create student profile if not exists
            if (studentRepository.findByUser_Id(userId).isEmpty()) {
                Student student = new Student();
                student.setUser_id(userId);
                student.setCode(user.getUsername());
                student.setFullname(user.getUsername());
                // Student doesn't have email/phone fields, use address as placeholder
                student.setAddress(user.getEmail());
                student.setIsActive(true);
                student.setCreatedAt(LocalDateTime.now());
                student.setUpdatedAt(LocalDateTime.now());
                studentRepository.save(student);
            }
        }
    }

    @Transactional
    public UserRole update(UUID id, UUID userId, UUID roleId, Boolean isActive) {
        UserRole ur = userRoleRepository.findById(id).orElseThrow();

        if (userId != null) {
            userRepository.findById(userId).orElseThrow();
            ur.setUserId(userId);
        }
        if (roleId != null) {
            roleRepository.findById(roleId).orElseThrow();
            ur.setRoleId(roleId);
        }
        if (isActive != null) {
            ur.setIsActive(isActive);
        }
        ur.setUpdatedAt(LocalDateTime.now());
        return userRoleRepository.save(ur);
    }

    @Transactional
    public void softDelete(UUID id) {
        UserRole ur = userRoleRepository.findById(id).orElseThrow();
        ur.setDeletedAt(LocalDateTime.now());
        ur.setIsActive(Boolean.FALSE);
        userRoleRepository.save(ur);
    }
}

