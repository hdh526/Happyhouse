import axios from "axios";
// import { API_BASE_URL } from "@/config";
const API_BASE_URL = "http://localhost:80/happyhouse";

// axios 객체 생성
function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

// function houseInstance() {
//   const instance = axios.create({
//     baseURL: APT_DEAL_URL,
//     headers: {
//       "Content-type": "application/json",
//     },
//   });
//   return instance;
// }

export { apiInstance };
