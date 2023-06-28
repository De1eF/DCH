<template>
    <h1>{{ loginType }}</h1>
    <div class="form">
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
        <button @click="submit">Submit</button>
    </div>
</template>

<script>
export default {
    name: 'Login',
    components: {

    },
    data() {
        return {
            loginType: 'login',
            loginTypes: ['login', 'register'],
            inputFs: [{
                name: 'email',
                type: 'email',
                label: 'Email',
                loginType: 'login',
                loginType2: 'register'
            }, {
                name: 'username',
                type: 'text',
                label: 'Username',
                loginType: null,
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
            }],
            email: 'test',
            username: '',
            password: '',
            password2: '',
            reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/,
            url: ''
        }
    },
    methods: {
        submit() {
            if (this.loginType === 'login') {
                this.login();
            } else if (this.loginType === 'register') {
                this.register();
            } else if (this.loginType === 'email') {
                this.FEmail();
            }
        },
        register() {
            this.email = document.getElementById('email').value;
            this.username = document.getElementById('username').value;
            this.password = document.getElementById('password').value;
            this.password2 = document.getElementById('password2').value;
            if (this.reg.test(this.email)) {
                console.log('valid email')
            } else {
                console.log('invalid email')
                return false;
            }
            if (this.password === this.password2) {
                console.log('passwords match')
            } else {
                console.log('passwords do not match')
                return false;
            } if (this.username.length > 3 && this.username.length < 16) {
                console.log('username is valid')
            } else {
                console.log('username is invalid')
                return false;
            }
            if (this.password.length > 3 && this.password.length < 16) {
                console.log('password is valid')
            } else {
                console.log('password is invalid')
                return false;
            }
            this.url = window.location.href.split(':8080')[0]

            fetch(this.url + ':1290/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: this.email,
                    username: this.username,
                    password: this.password
                })
            }).then(res => res.json())
                .then(data => {
                    console.log(data)
                })
        },
        login() {
            this.email = document.getElementById('email').value;
            this.password = document.getElementById('password').value;
            if (this.reg.test(this.email)) {
                console.log('valid email')
            } else {
                console.log('invalid email')
                return false;
            }
            this.url = window.location.href.split(':8080')[0]
            fetch(this.url + ':1290/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    login: this.email,
                    password: this.password
                })
            }).then(res => res.json())
                .then(data => {
                    console.log(data)
                    document.cookie = `token=${data.token}`
                    document.cookie = `email=${data.username}`
                    localStorage.setItem('token', data.token)
                    localStorage.setItem('email', data.username)
                    localStorage.setItem('username', "nousername")
                    this.$emit('update')
                    this.$router.push('/select-character')
                })
        }
    }
}
</script>

<style scoped>
.form {
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

