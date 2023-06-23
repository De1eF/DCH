<template>
    <div class="bg">
        <div class="line">
            <span class="line-title v-long">ХП:</span>
            <input @change="sendHealth" type="number" class="line-input num short" v-model="character.hp">
            <span class="line-title short">/</span>
            <input @change="sendHealth" type="number" class="line-input num short" v-model="character.maxhp">
        </div>
        <div class="line">
            <span class="line-title v-long">Додаткове ХП:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.bonusHp">
        </div>
        <div class="line">
            <span class="line-title v-long">Кубик хп:</span>
            <span class="line-title long"></span>
            <span class="line-title short"> d</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.hitDice">
        </div>
        <div class="line">
            <span class="line-title v-long">Рятівні кидки від смерті:</span>
            <span class="line-title mdium"></span>
        </div>
        <div class="line">
            <span class="line-title medium">Успіхи:</span>
            <input @change="sendHealth" type="checkbox" class="prof-checkbox" v-model="character.deathSavesSuccess[0]">
            <input @change="sendHealth" type="checkbox" class="prof-checkbox" v-model="character.deathSavesSuccess[1]">
            <input @change="sendHealth" type="checkbox" class="prof-checkbox" v-model="character.deathSavesSuccess[2]">
            <span class="line-title medium"></span>
        </div>
        <div class="line">
            <span class="line-title medium">Невдачі:</span>
            <input @change="sendHealth" type="checkbox" class="prof-checkbox" v-model="character.deathSavesFail[0]">
            <input @change="sendHealth" type="checkbox" class="prof-checkbox" v-model="character.deathSavesFail[1]">
            <input @change="sendHealth" type="checkbox" class="prof-checkbox" v-model="character.deathSavesFail[2]">
            <span class="line-title medium"></span>
        </div>
        <div class="line">
            <span class="line-title v-long">Клас броні:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.armorClass">
            <span class="line-title long">Ініціатива:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.initiative">
        </div>
        <div class="line">
            <span class="line-title medium">Швидкість:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.speed">
            <span class="line-title medium">Натхнення:</span>
            <input @change="sendHealth" type="checkbox" class="prof-checkbox" v-model="character.inspiration">
            <span class="line-title v-short"></span>
        </div>
        <div class="line">
            <span class="line-title v-long">Пасивна мудрість:</span>
            <span class="line-title medium">{{ passiveWisdom }}</span>
        </div>
        <div class="line">
            <span class="line-title v-long">Гроші:</span>
            <span class="line-title short">ММ:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.coins.cp">
            <span class="line-title short">СМ:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.coins.sp">
        </div>
        <div class="line">
            <span class="line-title short">ЕМ:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.coins.ep">
            <span class="line-title short">ЗМ:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.coins.gp">
            <span class="line-title short">ПМ:</span>
            <input @change="sendHealth" type="number" class="line-input short num" v-model="character.coins.pp">
        </div>
        <div class="add">
            <input @change="sendHealth" class="input-item-name" v-model="addNewSkill" type="text"
                placeholder="Назва навички">
            <button class="add-button" @click="addSkill">Додати</button>
        </div>
        <ol class="inventory">
            <li class="item" v-for="item in character.additionalSkills">
                <span class="item-name">{{ item.skillname }}</span>
                <button @click="deleteItem(item)" class="delete">X</button>
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
                armorClass: 0,
                hp: 0,
                maxhp: 0,
                hitDice: 8,
                bonusHp: 0,
                deathSavesSuccess: [false, false, false],
                deathSavesFail: [false, false, false],
                initiative: 0,
                speed: 0,
                inspiration: false,
                coins: {
                    cp: 0,
                    sp: 0,
                    ep: 0,
                    gp: 0,
                    pp: 0
                },
                additionalSkills: [
                    { skillname: "Дварфська витривалість", quantity: false },
                ],

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
            addNewSkill: "",
            timer: null,
            passiveWisdom: 0,
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

        },
        calculatePassiveWisdom() {
            this.passiveWisdom = 10 + Math.floor((this.character.abillityScores.Wisdom - 10) / 2) + (this.character.abilitiesProficiency.Wisdom ? this.character.proficiencyBonus : 0);
        },
        deleteItem(item) {
            this.character.additionalSkills.splice(this.character.additionalSkills.indexOf(item), 1);
        },
        addSkill() {
            this.character.additionalSkills.push({
                skillname: this.addNewSkill,
            });
        },
        update() {
            this.character = this.characterIn;
            if (this.character.deathSavesSuccess == null) {
                this.character.deathSavesSuccess = [false, false, false];

            }
            if (this.character.deathSavesFail == null) {
                this.character.deathSavesFail = [false, false, false];
            }
            this.character.coins = this.characterIn.coins || {
                copper: 0,
                silver: 0,
                electrum: 0,
                gold: 0,
                platinum: 0
            };
            if (this.character.additionalSkills == null) {
                this.character.additionalSkills = [];
            }
            this.character.proficiencyBonus = this.characterIn.proficiencyBonus || 2;
            console.log(this.character);
            console.log(this.character.abillityScores.Wisdom);
            this.calculatePassiveWisdom();
        }, sendHealth() {
            this.$emit('sendHealth', this.character)
        }

    },
    mounted() {
        this.levelCalulate();
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
    flex-direction: row;
    justify-content: space-between;

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

.v-short {
    width: 5px;
}

.medium {
    width: 100px;
}

.prof-checkbox {
    float: right;
    margin-right: 0;
    width: 20px;
    height: 20px;
    border-radius: 25%;
    background-color: #555;
    margin: 5px;
    margin-top: 20px;
    flex-shrink: 0;
}

.prof-checkbox:checked {
    background-color: #4aa8e6;
    border: 1px solid white;
}

h2 {
    margin-top: 40px;
    margin-bottom: 0;
}

ol {
    color: white;
    width: 350px;
    margin: 0 0;
    list-style: none;
    height: 175px;
    overflow-y: scroll;
    overflow-x: hidden;
}

li {
    color: white;
    font-size: 20px;
    text-align: left;
    margin: 5px 0;

}

.inventory {
    padding: 0;
    width: 350px;
    margin: 0 25px;
    padding-right: 15px;
}

.item {
    height: 35px;
    background-color: #444;
    padding: 5px;
    display: inline-flex;
    width: 100%;
    justify-content: space-between;

}

.item-name {
    width: 350px;
    display: inline-flex;
    align-items: center;
    max-width: 350px;
    overflow: hidden;
    word-wrap: normal;
    font-size: small;
    margin-right: 10px;
}


.input-item-name {
    width: 250px;
    height: 30px;
    margin-right: 5px;
    margin-left: 0;
    background-color: #444;
    border: none;
    border-radius: 5px;
    color: white;
    text-align: left;
    padding-left: 10px;
    flex-grow: 3;
}

.add {
    margin-top: 25px;
    width: 350px;
    display: inline-flex;
    padding: 0;
    margin-bottom: 10px;
}

.add-button {
    width: 100px;
    height: 32px;
    margin-right: -10px;
    margin-left: auto;
    background-color: #444;
    border: none;
    border-radius: 5px;
    color: white;
    text-align: center;
}

.delete {
    background-color: #333;
    color: darkred;
    border: solid 1px darkred;
    border-radius: 5px;
}
</style>

