import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import Login from '../views/Login.vue'
import CharacterSheet from '../views/CharacterSheet/CharacterSheet.vue'
import NotFound from '../views/NotFound.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    component: About
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  }
  , {
    path: '/character-sheet',
    name: 'character-sheet',
    component: CharacterSheet
  }, {
    path: '/character-sheet/:id',
    name: 'character-sheet-id',
    component: CharacterSheet
  },
  // 404
  {
    path: '/:catchAll(.*)',
    name: 'not-found',
    component: NotFound
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
