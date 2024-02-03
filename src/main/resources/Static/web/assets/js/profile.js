const CLIENT = '/api/clients/current';
const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            data: [],
            address: "",
            phone: "",
            province: "",
            country: "",
            city: "",
            zipCode: "",
            selectedAddress: null,
            addresses: [],
            purchases: [],
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get(CLIENT)
                .then(response => {
                    this.data = response.data;
                    console.log(this.data)
                    this.purchases = this.data.purchaseOrders;
                    this.addresses = this.data.addresses.map(address => {
                        return { ...address, expanded: false };
                    });
                })
                .catch(error => {
                    console.log(error);
                });
        },
        createAddress() {
            body = {
                "address": this.address,
                "phone": this.phone,
                "province": this.province,
                "country": this.country,
                "city": this.city,
                "zipCode": this.zipCode
            }
            axios.post('/api/clients/newaddress', body)
                .then(response => {
                    console.log(response);
                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: 'Address added successfully!',
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'OK'
                    });
                })
                .catch(error => {
                    console.log(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error!',
                        text: 'Error adding address!',
                        confirmButtonColor: '#d33',
                        confirmButtonText: 'OK'
                    });
                });
        },
        showAddressModal(address) {
            this.selectedAddress = address;
            this.$nextTick(() => {
                if (this.$refs.addressModal) {
                    this.$refs.addressModal.show();
                }
            });
        },
        clearSelectedAddress() {
            this.selectedAddress = null;
        },
    }
}).mount('#app');