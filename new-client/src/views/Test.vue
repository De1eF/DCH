<template>
    <input type="file" @change="uploadImage" />
    <img :src="picture" alt="" />
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

            reader.onloadend = function () {
                this.picture = reader.result
                console.log(this.picture)
            }
            reader.readAsDataURL(file);

        },
        downloadImage() {
            console.log("download1")
            this.image = new Image();
            console.log("download2")
            this.image.src = this.picture;
            console.log("download3")
            document.body.appendChild(this.image);
            console.log("download4")

        }

    }
}

</script>
<style scoped></style>