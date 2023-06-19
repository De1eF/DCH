<template>
    <div class="bg-top">
        <div class="panel">
            <GeneralInfo :characterIn="character"></GeneralInfo>
        </div>
        <div class="panel">
            <Health :characterIn="character"></Health>
        </div>
        <div class="panel">
            <Inventory></Inventory>
        </div>
        <div class="panel">
            <Skills @sendSkills="saveSkills" :skillsSet="character.paramMap.skills" :proficiency_bonusSet="1"></Skills>
        </div>
    </div>
</template>


<script>
import Inventory from './Inventory.vue'
import Skills from './Skills.vue'
import GeneralInfo from './GeneralInfo.vue'
import Health from './Health.vue'
export default {
    name: 'CharacterSheet',
    data() {
        return {
            character: {
                name: '',
                paramMap: {
                    skills: {}
                }
            }
        }
    },
    components: {
        Inventory,
        Skills,
        GeneralInfo,
        Health
    }, methods: {
        getCharacter() {
            this.token = localStorage.getItem('token')
            this.url = window.location.href.split(':8080')[0]
            fetch(this.url + `:1290/characters/${this.$route.params.id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.token
                }
            }
            )
                .then(response => response.json())
                .then(data => {
                    this.character = data
                    if (Object.keys(this.character.paramMap.skills).length == 0) {
                        this.character.paramMap.skills = {
                            "Acrobatics": false,
                            "Animal Handling": false,
                            "Arcana": false,
                            "Athletics": false,
                            "Deception": false,
                            "History": false,
                            "Insight": false,
                            "Intimidation": false,
                            "Investigation": false,
                            "Medicine": false,
                            "Nature": false,
                            "Perception": false,
                            "Performance": false,
                            "Persuasion": false,
                            "Religion": false,
                            "Sleight of Hand": false,
                            "Stealth": false,
                            "Survival": false
                        }
                    }
                }).then(() => {
                    this.$forceUpdate()
                })

        }, sendChanges() {
            this.token = localStorage.getItem('token')
            this.url = window.location.href.split(':8080')[0]
            console.log(this.character)
            fetch(this.url + `:1290/characters/${this.$route.params.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.token
                }
                , body: JSON.stringify(this.character)
            }
            )
        },
        saveSkills(skills) {
            this.character.paramMap.skills = skills
            this.sendChanges()
        }

    },
    mounted() {
        this.getCharacter()
    }
}


</script>

<style scoped>
.bg-top {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    width: 100%;
    height: 100%;
    flex-wrap: wrap;
}

.panel {
    width: 400px;
    height: 700px;
    margin: 15px;
    border-radius: 15px;
    background-color: #333;
    padding: auto;
    display: flex;
}
</style>