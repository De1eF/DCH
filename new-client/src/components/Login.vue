<template>
    <h1>{{ loginType }}</h1>
    <form>
        <label for="loginType">Login Type</label>
        <select v-model="loginType">
            <option v-for="lType in loginTypes">{{ lType }}</option>
        </select>
        <br>
        <div v-for="inputF in inputFs" class="input-group">
            <Transition class="fade">
                <div v-if="loginType === inputF.loginType || loginType === inputF.loginType2" class="input-group-prepend">
                    <label :for="inputF.name">{{ inputF.label }}</label>
                    <input :type="inputF.type" :name="inputF.name" :id="inputF.name" required>
                </div>
            </Transition>
        </div>
        <button type="submit">Submit</button>
    </form>
</template>

<script>
export default {
    components: {

    },
    data() {
        return {
            loginType: 'login',
            loginTypes: ['login', 'register', 'email'],
            inputFs: [{
                name: 'email',
                type: 'email',
                label: 'Email',
                loginType: 'email',
                loginType2: 'register'
            }, {
                name: 'username',
                type: 'text',
                label: 'Username',
                loginType: 'login',
                loginType2: 'register'
            }, {
                name: 'password',
                type: 'password',
                label: 'Password',
                loginType: 'login',
                loginType2: 'register'
            }, {
                name: 'password2',
                type: 'password',
                label: 'Confirm Password',
                loginType: 'register',
                loginType2: null
            }]

        }
    },
    methods: {
    }
}
</script>

<style scoped>
form {
    max-width: 420px;
    margin: 30px auto;
    background-color: #333;
    text-align: left;
    padding: 40px;
    border-radius: 10px;
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
    width: 100%;
    box-sizing: border-box;
    border: none;
    border-bottom: rgb(0, 236, 236) 3px solid;
    color: #555;
}

select {
    color: #222;
    display: inline-block;
    text-transform: uppercase;
    letter-spacing: 1px;
    font-weight: bold;
    width: 100%;
    padding: 10px 6px;
    margin-bottom: 0;
}

button {
    width: 100%;
    padding: 10px 6px;
    margin-top: 50px;
    text-transform: uppercase;
    letter-spacing: 1px;
    font-weight: bold;
}

h1 {
    color: white;
    text-transform: capitalize;
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
    margin: 0 0 -100px 0;
}
</style>

