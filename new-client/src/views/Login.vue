<template>
    <h1>{{ loginType }}</h1>
    <div class="form">
        <label for="loginType">Login Type</label>
        <select @change="changeType" v-model="loginType">
            <option v-for="lType in loginTypes">{{ lType }}</option>
        </select>
        <br>
        <Transition class="fade">
            <div v-if="showEmail" class="input-group-prepend">
                <label :for="email">email</label>
                <input type="email" :name="email" v-model="email" required>
            </div>
        </Transition>
        <Transition class="fade">
            <div v-if="showUsername" class="input-group-prepend">
                <label :for="username">username</label>
                <input type="text" :name="username" v-model="username" required>
            </div>
        </Transition>
        <Transition class="fade">
            <div v-if="showPassword" class="input-group-prepend">
                <label :for="password">password</label>
                <input type="password" :name="password" v-model="password" required>
            </div>
        </Transition>
        <Transition class="fade">
            <div v-if="showPassword2" class="input-group-prepend">
                <label :for="password2">repeat password</label>
                <input type="password" :name="password2" v-model="password2" required>
            </div>
        </Transition>

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
            showEmail: false,
            showUsername: false,
            showPassword: false,
            showPassword2: false,
            loginType: 'login',
            loginTypes: ['login', 'register', "email"],

            email: '',
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
        }, changeType() {
            if (this.loginType === 'login') {
                this.showEmail = true;
                this.showPassword = true;
                this.showUsername = false;
                this.showPassword2 = false;
            } else if (this.loginType === 'register') {
                this.showEmail = true;
                this.showPassword = true;
                this.showUsername = true;
                this.showPassword2 = true;
            } else if (this.loginType === 'email') {
                this.showEmail = true;
                this.showPassword = false;
                this.showUsername = false;
                this.showPassword2 = false;
            }
        },
        register() {
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
            } if (this.username.length > 4 && this.username.length < 16) {
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
                    this.loginType = 'login'
                    this.changeType()
                })
        },
        login() {
            if (this.reg.test(this.email)) {
                console.log('valid email')
            } else {
                console.log('invalid email')
                return false;
            }
            this.url = window.location.href.split(':8080')[0]
            console.log(this.email, this.password)
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
        },
        FEmail() {
            if (this.reg.test(this.email)) {
                console.log('valid email')
            } else {
                console.log('invalid email')
                return false;
            }
            this.url = window.location.href.split(':8080')[0]
            fetch(this.url + ':1290/login-email', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: this.email
                })
            }).then(res => res.json())
                .then(data => {
                    console.log(data)
                    this.loginType = 'login'
                    this.changeType()
                }
                )
        }
    }, mounted() {
        this.changeType()
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

