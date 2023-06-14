<template>
    <div v-if="$route.params.id != null">
        <h2>Character Id: {{ $route.params.id }}</h2>
    </div>

    <div class="bg-top">
        <div class="panel">
            <Inventory></Inventory>
        </div>
        <div class="panel">
            <Skills></Skills>
        </div>
    </div>
</template>


<script>
import Inventory from './Inventory.vue'
import Skills from './Skills.vue'
export default {
    name: 'CharacterSheet',
    components: {
        Inventory,
        Skills
    }
}
</script>

<style scoped>
.bg-top {
    display: flex;
}

.panel {
    width: 450px;
    height: 700px;
    margin: 25px;
    border-radius: 15px;
    background-color: #333;
    padding: auto 125px
}
</style>