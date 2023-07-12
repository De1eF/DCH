<template>
    <input type="file" @change="uploadImage" />
    <img :src="image" alt="" />
    <input type="number" v-model="id" />
    <button @click="downloadImage">Download</button>
</template>
<script>
export default {
    name: 'Test',
    data() {
        return {
            username: '',
            id: -1,
            characters: [],
            token: '',
            url: '',
            picture: '',
            image: '',
        }
    },
    methods: {
        uploadImage() {
            const file = document.querySelector('input[type=file]').files[0]
            const reader = new FileReader()
            this.token = localStorage.getItem('token')

            reader.onloadend = () => {
                this.picture = reader.result
                console.log(this.picture)
                this.url = window.location.href.split(':8080')[0]
                this.token = localStorage.getItem('token')
                fetch(this.url + ":1290/portraits", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + this.token
                    },
                    body: JSON.stringify({
                        data: this.picture
                    })
                })
            }

            reader.readAsDataURL(file);

        },
        downloadImage() {
            console.log('id', this.id)
            this.url = window.location.href.split(':8080')[0]
            this.token = localStorage.getItem('token')
            fetch(this.url + ":1290/portraits/" + this.id, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.token
                }
            }).then(res => res.json())
                .then(data => {
                    console.log(data)
                    this.image = data.data
                })
        }

    }, mounted() {
        this.url = window.location.href.split(':8080')[0]
        console.log(this.url)
    }
}

</script>
<style scoped></style>