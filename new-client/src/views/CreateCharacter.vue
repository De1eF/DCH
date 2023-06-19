<template>
    <div @click.self="close" class="backdrop">
        <div class="modal">
            <h1>Create Character</h1>
            <h2>For: {{ username }}</h2>
            <div class="form">
                <label for="name">Character Name</label>
                <input type="text" name="name" id="name" required>
                <button @click="create">Create</button>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'CreateCharacter',
    props: ["username"],
    methods: {
        create() {
            console.log('create')
            this.$emit('create', document.getElementById('name').value)
        }, close() {
            console.log('close')
            this.$emit('close')
        }
    }

}
</script>
<style scoped>
.modal {
    width: 500px;
    height: 350px;
    margin: auto;
    border-radius: 15px;
    background-color: #333;
    padding: auto;
    padding-top: 15px;

}

.backdrop {
    background-color: rgba(0, 0, 0, 0.5);
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
}

h1 {
    color: white;
    text-transform: capitalize;
}

label {
    color: #eee;
    display: inline-block;
    margin: 25px 0 15px;
    text-transform: uppercase;
    letter-spacing: 1px;
    font-weight: bold;
}

input {
    display: block;
    padding: 10px 6px;
    width: 80%;
    box-sizing: border-box;
    border: none;
    border-bottom: rgb(0, 236, 236) 3px solid;
    color: #555;
    margin: auto;
}

button {
    width: 80%;
    padding: 10px 6px;
    margin-top: 25px;
    text-transform: uppercase;
    letter-spacing: 1px;
    font-weight: bold;
}
</style>