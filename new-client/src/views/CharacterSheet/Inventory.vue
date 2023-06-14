<template>
    <div class="bg">
        <h1>Inventory</h1>
        <h2>Weight: {{ countWeight() }} lb.</h2>
        <div class="add">
            <input class="input-item-name" v-model="newItemName" type="text" placeholder="Item Name">
            <button class="add-button" @click="addItem">Add Item</button>
        </div>
        <ol class="inventory">
            <li class="item" v-for="item in inventory">
                <span class="item-name">{{ item.itemName }}</span>
                <input @change="deleteIfZero(item)" class="item-property" v-model="item.quantity" type="number">
                <span class="lb"></span>
                <input @change="countWeight" class="item-property" v-model="item.weight" type="number">
                <span class="lb">lb.</span>
            </li>
        </ol>
    </div>
</template>


<script>
export default {
    name: 'Inventory',
    data() {
        return {
            newItemName: '',
            inventory: [
                { itemName: 'Item 1', quantity: 1, weight: 1.00 },
            ]
        }
    },
    methods: {
        addItem() {
            this.inventory.push({ itemName: this.newItemName, quantity: 1, weight: 0 });
            this.newItemName = '';
        }, countWeight() {
            let totalWeight = 0;
            for (let i = 0; i < this.inventory.length; i++) {
                totalWeight += this.inventory[i].weight * this.inventory[i].quantity;
            }
            return totalWeight;
        }, deleteIfZero(item) {
            if (item.quantity == 0) {
                this.inventory.splice(this.inventory.indexOf(item), 1);
            }
        }
    }
}
</script>

<style scoped>
ol {
    color: white;
    width: 400px;
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

.inventory {
    padding: 0;
    width: 400px;
    margin: 0 25px;
    padding-right: 15px;
}

.item {
    height: 35px;
    background-color: #444;
    padding: 5px;
    display: inline-flex;
    width: 100%;
}

.item-name {
    width: 200px;
    flex-grow: 2;
    display: inline-flex;
    align-items: center;
}

.item-property {
    width: 50px;
    height: 30px;
    /*stick to right side*/
    margin-right: 5px;
    margin-left: auto;
    background-color: #333;
    border: none;
    border-radius: 5px;
    color: white;
    text-align: right;
    ;
}

.lb {
    margin-left: 5px;
    margin-right: 5px;
    display: inline-flex;
    align-items: center;
}

.input-item-name {
    width: 300px;
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
    width: 400px;
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
</style>

