<template>
  <Login />
</template>

<script>
import Login from './components/Login.vue'
import Skills from './components/Skills.vue'
export default {
  name: 'App',
  components: {
    Login,
    Skills
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

body {
  margin: 0;
  background-color: #222;
}
</style>
