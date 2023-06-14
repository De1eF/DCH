<template>
    <div class="bg">
        <h1>Skills</h1>
        <ol>
            <li v-for="skill in sortedArray(skills)">
                {{ skill.ua_name }}
                <span class="ability">{{ ability[skill.ability].substring(0, 3) }}.</span>
                <input class="prof-checkbox" type="checkbox" v-model="skill.prof">
            </li>
        </ol>
    </div>
</template>


<script>
export default {
    name: 'Skills',
    data() {
        return {
            skills: [{ name: "Acrobatics", ability: "Dexterity", ua_name: "Акробатика", prof: true },
            { name: "Animal Handling", ability: "Wisdom", ua_name: "Догляд за тваринами", prof: false },
            { name: "Arcana", ability: "Intelligence", ua_name: "Магія", prof: false },
            { name: "Athletics", ability: "Strength", ua_name: "Атлетика", prof: false },
            { name: "Deception", ability: "Charisma", ua_name: "Обман", prof: false },
            { name: "History", ability: "Intelligence", ua_name: "Історія", prof: false },
            { name: "Insight", ability: "Wisdom", ua_name: "Розуміння", prof: false },
            { name: "Intimidation", ability: "Charisma", ua_name: "Залякування", prof: false },
            { name: "Investigation", ability: "Intelligence", ua_name: "Розслідування", prof: false },
            { name: "Medicine", ability: "Wisdom", ua_name: "Медицина", prof: false },
            { name: "Nature", ability: "Intelligence", ua_name: "Природа", prof: false },
            { name: "Perception", ability: "Wisdom", ua_name: "Спостережливість", prof: true },
            { name: "Performance", ability: "Charisma", ua_name: "Виконання", prof: false },
            { name: "Persuasion", ability: "Charisma", ua_name: "Переконання", prof: false },
            { name: "Religion", ability: "Intelligence", ua_name: "Релігія", prof: false },
            { name: "Sleight of Hand", ability: "Dexterity", ua_name: "Спритність рук", prof: false },
            { name: "Stealth", ability: "Dexterity", ua_name: "Хитрість", prof: false },
            { name: "Survival", ability: "Wisdom", ua_name: "Виживання", prof: false }
            ],
            ability: {
                "Strength": "Cила",
                "Dexterity": "Спритність",
                "Constitution": "Стійкість",
                "Intelligence": "Інтелект",
                "Wisdom": "Мудрість",
                "Charisma": "Харизма"
            }

        }
    },
    methods: {
        sortedArray(x) {
            return x.sort((a, b) => (a.ua_name > b.ua_name) ? 1 : -1)
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
    padding: 0;
}

li {
    color: white;
    font-size: 20px;
    text-align: left;
    margin: 10px 0;
}

/* remove style from imnput */
input {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    -moz-outline-style: none;
    border: none;
    outline: none;
}

.prof-checkbox {
    float: right;
    margin-right: 20px;
    width: 20px;
    height: 20px;
    border-radius: 25%;
    background-color: #555;
}

.prof-checkbox:checked {
    background-color: #4aa8e6;
    border: 1px solid white;
}

.bg {
    width: 100%;
    height: 100%;
    margin: auto 40px auto 10px;
}


.ability {
    color: grey;
    font-size: 12px;
}
</style>

