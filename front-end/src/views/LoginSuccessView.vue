<template>
  <div class="loginsuccesspage"></div>
</template>

<script>
export default {
  mounted() {
    const naver_id_login = new window.naver_id_login("9lbytc1dbrdcUCa6AHG_", "http://localhost:8080/login/naver");
    alert(naver_id_login.oauthParams.access_token);
    console.log(naver_id_login, "sdfsdfsdf");
  },

  name: "LoginSuccessView",
};
</script>

<style scoped></style>
