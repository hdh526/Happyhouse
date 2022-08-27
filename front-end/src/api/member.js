import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  console.log("member.js");
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(id, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${id}`).then(success).catch(fail);
}

function checkDuplicate(id, success, fail) {
  api.get(`/user/check/${id}`).then(success).catch(fail);
}
// function logout(success, fail)

export { login, findById, checkDuplicate };
