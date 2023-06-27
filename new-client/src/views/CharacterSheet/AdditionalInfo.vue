<template>
    <div class="bg">
        <h1>Додаткова інформація</h1>
        <div class="add">
            <input class="input-item-name" v-model="newItemName" type="text" placeholder="Назва предмету">
            <button class="add-button" @click="addItem">Додати</button>
        </div>
        <ol class="inventory">
            <li class="item" v-for="item in OutMap">
                <span class="item-name">{{ item }}</span>
                <button @click="deleteItem(item)" class="delete">X</button>
            </li>
        </ol>
    </div>
</template>


<script>
export default {
    props: {
        paramMap: Object,
    },

    name: 'Inventory',
    data() {
        return {
            newItemName: '',
            timer: null,
            OutMap: []
        }
    },
    methods: {
        addItem() {
            this.OutMap.push(this.newItemName);
            this.newItemName = '';
            this.saveInventory();
        }, saveInventory() {
            this.$emit('sendInventory', this.OutMap);
        }, deleteItem(item) {
            this.OutMap.splice(this.OutMap.indexOf(item), 1);
        },
        update() {
            if (this.paramMap.Traits) {
                this.OutMap = this.paramMap.Traits;
            }
        }
    },
    mounted() {
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
    color: white;
    width: 350px;
    margin: 0 0;
    list-style: none;
    height: 500px;
    overflow-y: scroll;
    overflow-x: hidden;
}

li {
    color: white;
    font-size: 20px;
    text-align: left;
    margin: 5px 0;

}


.bg {
    width: 100%;
    height: 100%;
    margin: auto 50px auto -5px;
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

ol {
    color: white;
    width: 350px;
    list-style: none;
    height: 550px;
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

