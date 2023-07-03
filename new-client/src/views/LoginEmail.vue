<template></template>

<script>
export default {
    name: 'Login',
    components: {

    },
    data() {
        return {
            token: '',
        }
    },
    methods: {
        login() {
            this.token = window.location.href.split('token=')[1]
            localStorage.setItem('token', this.token)
            localStorage.setItem('username', "nousername")
            this.$emit('update')
            this.$router.push('/select-character')

        },

    }, mounted() {
        this.login()
    }
}
</script>

<style scoped></style>

