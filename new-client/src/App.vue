<template>
  <Transition class="fade">
    <nav v-if="showNavBar">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link to="/select-character" v-if="token">Select Character</router-link><span v-if="token">|</span>
      <router-link to="/login" v-if="token">{{ username }}</router-link>
      <router-link to="/login" v-else>Login</router-link>
    </nav>
  </Transition>
  <router-view @update="update" />
  <div @click="toggleNavBar" class="hide-button" style="">
    <img v-if="showNavBar" class="hide-icon" src="./assets/hide.png" alt="">
    <img v-else class="hide-icon" src="./assets/view.png" alt="">
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      showNavBar: true,
      token: localStorage.getItem("token"),
      username: localStorage.getItem("username")
    };
  },
  methods: {
    toggleNavBar() {
      this.showNavBar = !this.showNavBar;
    },
    update() {
      this.token = localStorage.getItem("token");
      this.username = localStorage.getItem("username")

    },
  }, mounted() {
    this.timer = setInterval(() => {
      this.update()
    }, 100)
  },
  beforeDestroy() {
    clearInterval(this.timer)
  }

};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white
}

body,
html {
  margin: 0;
  padding: 0;
  background-color: #222;
}

nav {
  height: 50px;
  margin: 0;
  background-image: linear-gradient(to right, rgb(237, 34, 36), rgb(243, 91, 34), rgb(249, 150, 33), rgb(245, 193, 30), rgb(241, 235, 27) 27%, rgb(241, 235, 27), rgb(241, 235, 27) 33%, rgb(99, 199, 32), rgb(12, 155, 73), rgb(33, 135, 141), rgb(57, 84, 165), rgb(97, 55, 155), rgb(147, 40, 142));
  display: flex;
  align-items: center;
  justify-content: center;
}

nav a {
  transition: all 0.2s ease-in-out;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  padding: 7.5px;
  margin: auto 10px;
  border-radius: 10px;
  text-decoration: none;
}

nav a.router-link-exact-active {
  background-color: #222;
  color: #fff;
}

.hide-button {
  cursor: pointer;
  position: fixed;
  right: 0;
  top: 0;
  z-index: 100;
  width: 50px;
  height: 50px;
}

.hide-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  padding: 10px;
  box-sizing: border-box;
  transition: all 0.2s ease-in-out;
}

.fade-enter-active {
  transition: opacity .5s
}

.v-enter-active,
.v-leave-active {
  transition: transform 0.5s ease;
  transition: margin 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  transform: scale(1, 0);
  margin: 0 0 -50px 0;
}

/* ===== Scrollbar CSS ===== */
/* Firefox */
* {
  scrollbar-width: auto;
  scrollbar-color: #1f1f1f #ffffff;
}

/* Chrome, Edge, and Safari */
*::-webkit-scrollbar {
  width: 16px;
}

*::-webkit-scrollbar-track {
  background: #444;
}

*::-webkit-scrollbar-thumb {
  background-color: #333;
  border-radius: 10px;
  border: 3px solid #444;
}
</style>
