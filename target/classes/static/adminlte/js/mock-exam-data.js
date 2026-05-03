(function () {
  const ENABLE_MOCK = true; // đổi false hoặc xóa file/script tag khi không dùng nữa
  if (!ENABLE_MOCK) return;

  const REG_KEY = "mock_exam_registrations";
  const RES_KEY = "mock_exam_results";

  const seedRegistrations = [
    {
      id: "11111111-1111-1111-1111-111111111111",
      examId: "aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1",
      examRoomId: "bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1",
      studentId: "ccccccc1-cccc-cccc-cccc-ccccccccccc1",
      rollNumber: "SBD001",
      isActive: true
    },
    {
      id: "22222222-2222-2222-2222-222222222222",
      examId: "aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2",
      examRoomId: "bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbb2",
      studentId: "ccccccc2-cccc-cccc-cccc-ccccccccccc2",
      rollNumber: "SBD002",
      isActive: true
    }
  ];

  const seedResults = [
    {
      id: "33333333-3333-3333-3333-333333333333",
      registrationId: "11111111-1111-1111-1111-111111111111",
      score: 8.5,
      status: "PASS",
      isLocked: false,
      isActive: true
    },
    {
      id: "44444444-4444-4444-4444-444444444444",
      registrationId: "22222222-2222-2222-2222-222222222222",
      score: 4.0,
      status: "FAIL",
      isLocked: true,
      isActive: true
    }
  ];

  const read = (k, seed) => {
    const raw = localStorage.getItem(k);
    if (raw) return JSON.parse(raw);
    localStorage.setItem(k, JSON.stringify(seed));
    return seed;
  };
  const write = (k, v) => localStorage.setItem(k, JSON.stringify(v));
  const id = () => crypto.randomUUID ? crypto.randomUUID() : String(Date.now());

  const originalFetch = window.fetch.bind(window);

  window.fetch = async (url, options = {}) => {
    const method = (options.method || "GET").toUpperCase();
    const u = typeof url === "string" ? url : url.url;

    const apiType = u.includes("/api/exam-registrations")
      ? "reg"
      : u.includes("/api/exam-results")
      ? "res"
      : null;

    if (!apiType) return originalFetch(url, options);

    const key = apiType === "reg" ? REG_KEY : RES_KEY;
    const seed = apiType === "reg" ? seedRegistrations : seedResults;
    let data = read(key, seed);

    const base = apiType === "reg" ? "/api/exam-registrations" : "/api/exam-results";
    const idPart = u.replace(base, "").replace("/", "");

    if (method === "GET") {
      if (!idPart) return json(data);
      const row = data.find(x => x.id === idPart);
      return row ? json(row) : notFound();
    }

    if (method === "POST") {
      const body = JSON.parse(options.body || "{}");
      const created = { id: id(), ...body };
      data.push(created);
      write(key, data);
      return json(created, 201);
    }

    if (method === "PUT") {
      const body = JSON.parse(options.body || "{}");
      const idx = data.findIndex(x => x.id === idPart);
      if (idx < 0) return notFound();
      data[idx] = { ...data[idx], ...body, id: idPart };
      write(key, data);
      return json(data[idx]);
    }

    if (method === "DELETE") {
      data = data.filter(x => x.id !== idPart);
      write(key, data);
      return new Response(null, { status: 204 });
    }

    return originalFetch(url, options);
  };

  function json(body, status = 200) {
    return new Response(JSON.stringify(body), {
      status,
      headers: { "Content-Type": "application/json" }
    });
  }

  function notFound() {
    return json({ message: "Not found" }, 404);
  }

  // tiện reset nhanh trên console
  window.resetMockExamData = function () {
    localStorage.removeItem(REG_KEY);
    localStorage.removeItem(RES_KEY);
    alert("Mock data reset xong, reload trang.");
  };
})();
