<template>
    <div class="bg">
        <h1>Загальна інформація</h1>
        <div class="line">
            <span class="line-title">Ім'я:</span>
            <input @input="sendInfo" type="text" class="line-input" placeholder="Ім'я персонажа" v-model="character.name">
        </div>
        <div class="line">
            <span class="line-title">Раса:</span>
            <select @change="sendInfo" v-model="character.race" class="line-input">
                <option v-for="race in races" :value="race">{{ racesName[race] }}</option>
            </select>
        </div>
        <div class="line">
            <span class="line-title">Клас:</span>
            <select @change="sendInfo" v-model="character.class" class="line-input">
                <option v-for="Class in classes" :value="Class">{{ classesName[Class] }}</option>
            </select>
        </div>
        <div class="line">
            <span class="line-title long">Рівень: {{ level }}</span>
            <span class="line-title long">Досвід:</span>
            <input @input="levelCalulate" @click="levelCalulate" type="number" class="line-input num medium" placeholder="0"
                v-model="character.xp">
        </div>
        <div class="line">
            <span class="line-title v-long ">Бонус майстерності: {{ proficiencyBonus }}</span>
        </div>
        <div class="line">
            <span class="line-title">Походження:</span>
            <select @change="sendInfo" v-model="character.background" class="line-input">
                <option v-for="background in backgrounds" :value="background">{{ backgroundsName[background] }}</option>
            </select>
        </div>
        <div class="line">
            <span class="line-title">Світогляд:</span>
            <select @change="sendInfo" v-model="character.alignment" class="line-input">
                <option v-for="alignment in alignments" :value="alignment">{{ alignmentsName[alignment] }}</option>
            </select>
        </div>
        <h2>Характеристики</h2>
        <ol>
            <li v-for="ability in abilities" class="line">
                <span class="line-title long">{{ abilitiesName[ability] }} : {{ Math.floor((character.abilities[ability] -
                    10) / 2)
                }}</span>
                <input @input="sendInfo" type="number" class="line-input num short" placeholder="0"
                    v-model="character.abilities[ability]" maxlength="2">
                <input @input="toggle(character.abilitiesProficiency[ability])" class="prof-checkbox" type="checkbox"
                    v-model="character.abilitiesProficiency[ability]">
                <span class="line-title long">РК : {{ Math.floor((character.abilities[ability] -
                    10) / 2) + (character.abilitiesProficiency[ability] ? proficiencyBonus : 0)
                }}</span>
            </li>
        </ol>
    </div>
</template>


<script>

export default {
    name: 'GeneralInfo',
    props: {
        characterIn: Object,
    },
    data() {
        return {
            character: {
                name: '',
                race: '',
                class: '',
                xp: 0,
                background: '',
                alignment: '',
                abilities: {
                    Strength: 10,
                    Dexterity: 11,
                    Constitution: 12,
                    Intelligence: 13,
                    Wisdom: 0,
                    Charisma: 8
                },
                abilitiesProficiency: {
                    Strength: false,
                    Dexterity: false,
                    Constitution: false,
                    Intelligence: false,
                    Wisdom: false,
                    Charisma: false
                },
            },
            abilities: [
                "Strength",
                "Dexterity",
                "Constitution",
                "Intelligence",
                "Wisdom",
                "Charisma"
            ],
            abilitiesName: {
                "Strength": "Сила",
                "Dexterity": "Ловкість",
                "Constitution": "Тіло",
                "Intelligence": "Інтелект",
                "Wisdom": "Мудрість",
                "Charisma": "Харизма"
            },
            classes: [
                "Barbarian",
                "Bard",
                "Cleric",
                "Druid",
                "Fighter",
                "Monk",
                "Paladin",
                "Ranger",
                "Rogue",
                "Sorcerer",
                "Warlock",
                "Wizard"
            ],
            classesName: {
                "Barbarian": "Варвар",
                "Bard": "Бард",
                "Cleric": "Клерик",
                "Druid": "Друїд",
                "Fighter": "Воїн",
                "Monk": "Монах",
                "Paladin": "Паладін",
                "Ranger": "Слідопит",
                "Rogue": "Розбійник",
                "Sorcerer": "Чаклун",
                "Warlock": "Чорнокнижник",
                "Wizard": "Маг"
            },
            races: [
                "Dragonborn",
                "Dwarf",
                "Elf",
                "Gnome",
                "Half-Elf",
                "Half-Orc",
                "Halfling",
                "Human",
                "Tiefling"
            ],
            racesName: {
                "Dragonborn": "Драконорождений",
                "Dwarf": "Дворф",
                "Elf": "Ельф",
                "Gnome": "Гном",
                "Half-Elf": "Півельф",
                "Half-Orc": "Піворк",
                "Halfling": "Напіврослик",
                "Human": "Людина",
                "Tiefling": "Тіфлінг"
            },
            backgrounds: [
                "Acolyte",
                "Charlatan",
                "Criminal",
                "Entertainer",
                "Folk Hero",
                "Guild Artisan",
                "Hermit",
                "Noble",
                "Outlander",
                "Sage",
                "Sailor",
                "Soldier",
                "Urchin"
            ],
            backgroundsName: {
                "Acolyte": "Аколит",
                "Charlatan": "Шахрай",
                "Criminal": "Злочинець",
                "Entertainer": "Артист",
                "Folk Hero": "Народний герой",
                "Guild Artisan": "Майстер",
                "Hermit": "Самітник",
                "Noble": "Шляхтич",
                "Outlander": "Прибулець",
                "Sage": "Мудрець",
                "Sailor": "Моряк",
                "Soldier": "Солдат",
                "Urchin": "Бродяга"
            },
            alignments: [
                "Lawful Good",
                "Neutral Good",
                "Chaotic Good",
                "Lawful Neutral",
                "Neutral",
                "Chaotic Neutral",
                "Lawful Evil",
                "Neutral Evil",
                "Chaotic Evil"
            ],
            alignmentsName: {
                "Lawful Good": "Законно-добрий",
                "Neutral Good": "Нейтрально-добрий",
                "Chaotic Good": "Хаотично-добрий",
                "Lawful Neutral": "Законно-нейтральний",
                "Neutral": "Нейтральний",
                "Chaotic Neutral": "Хаотично-нейтральний",
                "Lawful Evil": "Законно-злий",
                "Neutral Evil": "Нейтрально-злий",
                "Chaotic Evil": "Хаотично-злий"
            },
            level: 0,
            levels: [
                0, 300, 900, 2700, 6500, 14000, 23000, 34000, 48000, 64000, 85000, 100000, 120000, 140000, 165000, 195000, 225000, 265000, 305000, 355000
            ], proficiencyBonus: 0,
            timer: null,
        }
    },
    methods: {
        levelCalulate() {
            for (let i = 0; i < this.levels.length; i++) {
                if (this.character.xp < this.levels[i]) {
                    this.level = i;
                    this.character.level = i;
                    this.proficiencyBonus = 2 + Math.floor((i - 1) / 4);
                    break;
                }
            }
            if (this.character.xp >= 355000) {
                this.level = 20;
                this.character.level = 20;
                this.proficiencyBonus = 6;
            }
            this.sendInfo();

        },
        sendInfo() {
            this.$emit('sendInfo', this.character);
        },
        toggle(item) {
            item = !item;
            this.update();
        },
        update() {
            this.character.name = this.characterIn.name;
            this.character.xp = this.characterIn.paramMap.experience;
            this.character.race = this.characterIn.paramMap.race;
            this.character.class = this.characterIn.paramMap.class;
            this.character.background = this.characterIn.paramMap.background;
            this.character.alignment = this.characterIn.paramMap.alignment;
            this.character.abilities = this.characterIn.paramMap.abillityScores;
            this.character.abilitiesProficiency = this.characterIn.paramMap.abilitiesProficiency;
        }

    },
    mounted() {
        this.character.name = this.characterIn.name;
        this.levelCalulate();
        console.log(this.characterIn);
        this.timer = setInterval(() => {
            this.update()
        }, 100)
    },
    beforeDestroy() {
        clearInterval(this.timer)
    }
}
</script>

<style scoped>
ol {
    list-style-type: none;
    margin: 0;
    padding: 0;
}

input {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border: none;
    outline: none;
}

.bg {
    width: 400px;
    height: 100%;
    margin: auto 0 auto 0;
    padding: auto;
}

.line {
    width: 400px;
    height: 35px;
    margin: 5px 0 auto 0;
    padding: auto;
    display: flex;

}

.line-title {
    width: 150px;
    height: 50px;
    margin: 20px 0 auto 0;
    padding: 0;
    font-size: 20px;
    color: white;
    float: left;
    align-items: left;
    justify-content: left;
    text-align: left;
    margin-left: 15px;
}

select {
    width: 75%;
    height: 35px;
    margin: 15px;
    padding: 0;
    font-size: 20px;
    color: white;
    background-color: #2d2d2d;
    border: 1px solid #2d2d2d;
    border-radius: 5px;
    outline: none;
    float: right;
}

.line-input {
    width: 75%;
    height: 35px;
    margin: 15px;
    padding: 0;
    font-size: 20px;
    color: white;
    background-color: #2d2d2d;
    border: 1px solid #2d2d2d;
    border-radius: 5px;
    outline: none;
    float: right;
}

.num {
    text-align: right;
}

.long {
    width: 200px;
}

.v-long {
    width: 300px;
}

.short {
    width: 50px;
}

.medium {
    width: 100px;
}

.prof-checkbox {
    float: right;
    margin-right: 0;
    width: 30px;
    height: 20px;
    border-radius: 25%;
    background-color: #555;
    margin: auto;
    margin-top: 20px;
}

.prof-checkbox:checked {
    background-color: #4aa8e6;
    border: 1px solid white;
}

h2 {
    margin-top: 40px;
    margin-bottom: 0;
}
</style>

