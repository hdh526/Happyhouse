import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import BoardView from "../views/BoardView.vue";
import ApartView from "../views/ApartView.vue";
import LoginSuccessView from "../views/LoginSuccessView.vue";
//import LoginView from "../views/LoginView.vue";
import KakaoMapHome from "@/components/map/KakaoMapHome.vue";
// import MypageView from "../views/MypageView.vue";
// import SignUpView from "../views/SignUpView.vue";
import store from "@/store/index.js";

Vue.use(VueRouter);

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo[0](token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다.");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/MemberView.vue"),
    children: [
      {
        path: "signin",
        name: "signIn",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "signup",
        name: "MemberRegister",
        component: () => import("@/components/user/MemberRegister.vue"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/MemberMyPage.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: BoardView,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "BoardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "BoardWrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardWrite.vue"),
      },
      {
        path: "detail/:no",
        name: "BoardDetail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "modify/:no",
        name: "BoardModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
  {
    path: "/apart",
    name: "Apart",
    component: ApartView,
    redirect: "/apart/search",
    children: [
      {
        path: "search",
        name: "ApartSearch",
        component: () => import("@/components/apart/ApartSearchView.vue"),
      },
      {
        path: "detail/:no",
        name: "ApartDetail",
        component: () => import("@/components/apart/ApartDetailView.vue"),
      },
    ],
  },
  {
    path: "/kakaohome",
    name: "KakaoMapHome",
    component: KakaoMapHome,
  },
  {
    path: "/login/naver",
    name: "naver",
    component: LoginSuccessView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
