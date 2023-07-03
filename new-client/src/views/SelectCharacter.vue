<template>
    <CreateCharacter @close="showCreation = false" v-if="showCreation" :username="username" @create="createCharacter">
    </CreateCharacter>
    <div class="bg">
        <h1>Select Character</h1>
        <ol>
            <li v-for="character in characters">
                <h2>{{ character.name }}</h2>
                <h3>Level: {{ character.level }}</h3>
                <router-link class="play-button" :to="'/character-sheet/' + character.id">Play</router-link>
            </li>
            <li class="new">
                <button class="play-button new-button" @click="showCreation = true">Create Character</button>
            </li>
        </ol>
    </div>
</template>


<script>
import CreateCharacter from './CreateCharacter.vue'

export default {
    name: 'Skills',
    components: {
        CreateCharacter
    },
    data() {
        return {
            username: '',
            id: -1,
            characters: [],
            token: '',
            showCreation: false,
            url: ''
        }
    },
    methods: {
        getUserName() {
            this.token = localStorage.getItem('token')
            this.url = window.location.href.split(':8080')[0]
            fetch(this.url + ':1290/users/me', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.token

                }
            }).then(res => res.json())
                .then(data => {
                    this.username = data.username
                    localStorage.setItem("username", this.username)
                    this.id = data.id
                    this.getCharacterList()
                })
        },
        getCharacterList() {
            this.url = window.location.href.split(':8080')[0]
            fetch(this.url + ':1290/characters/for-user/' + this.id, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.token,
                    'Access-Control-Allow-Origin': '*'
                }
            }).then(res => res.json())
                .then(data => {
                    this.characters = data
                })

        },
        createCharacter(name) {
            this.showCreation = false
            this.url = window.location.href.split(':8080')[0]
            fetch(this.url + ':1290/characters', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.token,
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({
                    name: name,
                    paramMap: {
                        inventory1: [],
                        skills: {},
                        abillityScores: {
                            "Strength": 10,
                            "Dexterity": 10,
                            "Constitution": 10,
                            "Intelligence": 10,
                            "Wisdom": 10,
                            "Charisma": 10
                        }, "abilitiesProficiency": {
                            "Strength": false,
                            "Dexterity": false,
                            "Constitution": false,
                            "Intelligence": false,
                            "Wisdom": true,
                            "Charisma": true
                        }
                    }
                })
            }).then(res => res.json())
                .then(data => {
                    this.getCharacterList();
                    this.$forceUpdate();
                    this.$emit('update')
                    this.$router.push('/character-sheet/' + data.id)
                }
                )

        }
    },

    mounted() {
        this.getUserName()


    }

}

</script>

<style scoped>
ol {
    padding: auto;
    margin: auto;
    display: flex;
    flex-wrap: wrap;
}

li {
    width: 500px;
    height: 150px;
    margin: 15px auto;
    background-color: #333;
    list-style: none;
    border-radius: 15px;
    padding: 15px;
    color: white;
}

li a {
    color: white;
    text-decoration: none;
}

.play-button {
    background-color: #333;
    border: 1px solid white;
    border-radius: 5px;
    padding: 5px;
    color: white;
    text-decoration: none;
    margin: 5px;
    font-size: x-large;
}

.new-button {
    margin: auto;
}

.new {
    display: flex;
}
</style>