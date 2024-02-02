const { createApp } = Vue

let app = createApp({

    data() {
        return {
            products: [],
            productCategory: [],
            productsSort: [],
            discount: [],
            price: 0,
            productsSale: [],
            productWithDiscount: [],
            isOpen1: false,
            isOpen2: false,
            isOpen3: false,
            isOpen4: false,
            selectedBrand: [],
            filteredBrandProducts: [],
        }
    },
    created() {
        this.loadData()

    },

    mounted() {
        this.filterByBrand();
    },

    computed: {
        uniqueBrand() {
            return [...new Set(this.products.map(product => product.brand))];
        },
    },

    methods: {
        loadData() {
            axios.get("/api/products")
                .then(response => {
                    this.products = response.data
                    this.filteredBrandProducts = this.products
                    this.productsSort = response.data.sort((a, b) => { return a.id - b.id })
                    console.log(this.productsSort)
                    this.productsSale = this.products.filter(product => product.discount > 0)
                    this.productWithDiscount = this.productsSale.forEach(product => {
                        const sale = product.price / 100 * product.discount
                        const newPrice = product.price - sale
                        product.discount = newPrice
                        console.log(product)
                        return product
                    })
                    console.log(this.productsSale)

                })
                .catch(error => {
                    console.log(error)
                })
        },

        productByCategory(category) {
            axios.get("/api/products/filtered?category=" + category)
                .then(response => {
                    this.products = response.data
                    this.filteredBrandProducts = this.products
                    this.productsSort = response.data.sort((a, b) => { return a.id - b.id })
                    console.log(this.productsSort)
                    this.productsSale = this.products.filter(product => product.discount > 0)
                    this.productWithDiscount = this.productsSale.forEach(product => {
                        const sale = product.price / 100 * product.discount
                        const newPrice = product.price - sale
                        product.discount = newPrice
                        console.log(product)
                        return product
                    })
                    console.log(this.productsSale)
                })
                .catch(error => {
                    console.log(error)
                })
        },

        dropDownMenu1() {
            this.isOpen1 = !this.isOpen1;
        },
        dropDownMenu2() {
            this.isOpen2 = !this.isOpen2;
        },
        dropDownMenu3() {
            this.isOpen3 = !this.isOpen3;
        },
        dropDownMenu4() {
            this.isOpen4 = !this.isOpen4;
        },
        categoryFilter(event) {
            const ALL = document.getElementById("ALL")
            const CPU = document.getElementById("CPU")
            const MONITOR = document.getElementById("MONITOR")
            const KEYBOARD = document.getElementById("KEYBOARD")
            const MOUSE = document.getElementById("MOUSE")
            const MOTHERBOARD = document.getElementById("MOTHERBOARD")
            const HEADPHONES = document.getElementById("HEADPHONES")
            const ACCESSORIES = document.getElementById("Accesories")

            if (event.target === ALL) {
                this.loadData()
            } else if (event.target === CPU) {
                this.productByCategory("CPU")
            } else if (event.target === MONITOR) {
                this.productByCategory("MONITOR")
            } else if (event.target === KEYBOARD) {
                this.productByCategory("KEYBOARD")
            } else if (event.target === MOUSE) {
                this.productByCategory("MOUSE")
            } else if (event.target === MOTHERBOARD) {
                this.productByCategory("MOTHERBOARD")
            } else if (event.target === HEADPHONES) {
                this.productByCategory("HEADPHONES")
            } else if (event.target === ACCESSORIES) {
                this.productByCategory("Accesories")
            }
        },
        filterByBrand() {
            this.filteredBrandProducts = this.products.filter(product => {
                return this.selectedBrand.length === 0 || this.selectedBrand.some(brand => product.brand === brand);
            });
        },
    } // fin methods
}) // fin create app

app.mount("#app")