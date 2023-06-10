<template>
    <div>
        <h1>Skills</h1>
        <p>Skills</p>
        <ol>
            <li v-for="skill in skills">{{ skill.ua_name }}
                <input class="prof-checkbox" type="checkbox" v-model="skill.prof">
            </li>
        </ol>
    </div>
</template>


<script>
export default {
    data() {
        return {
            skills: [{
                name: "Acrobatics",
                ability: "Dexterity",
                ua_name: "Акробатика",
                prof: true
            },
            {
                name: "Animal Handling",
                ability: "Wisdom",
                ua_name: "Догляд за тваринами",
                prof: false
            },
            {
                name: "Arcana",
                ability: "Intelligence",
                ua_name: "Магія",
                prof: false
            },
            {
                name: "Athletics",
                ability: "Strength",
                ua_name: "Атлетика",
                prof: false
            },
            {
                name: "Deception",
                ability: "Charisma",
                ua_name: "Обман",
                prof: false
            },
            {
                name: "History",
                ability: "Intelligence",
                ua_name: "Історія",
                prof: false
            },
            {
                name: "Insight",
                ability: "Wisdom",
                ua_name: "Розуміння",
                prof: false
            },
            {
                name: "Intimidation",
                ability: "Charisma",
                ua_name: "Залякування",
                prof: false
            },
            {
                name: "Investigation",
                ability: "Intelligence",
                ua_name: "Розслідування",
                prof: false
            },
            {
                name: "Medicine",
                ability: "Wisdom",
                ua_name: "Медицина",
                prof: false
            },
            {
                name: "Nature",
                ability: "Intelligence",
                ua_name: "Природа",
                prof: false
            }

            ]
        }
    }
}
</script>

<style scoped>
ol {
    color: white;
    width: 400px;
    margin: 0 auto;
    list-style: none;
}

li {
    color: white;
    font-size: 20px;
    text-align: left;
    margin: 10px 0;
}

.prof-checkbox {
    float: right;
    margin-right: 20px;
    width: 20px;
    height: 20px;

}

input {
    background-color: aqua;
}
</style>

