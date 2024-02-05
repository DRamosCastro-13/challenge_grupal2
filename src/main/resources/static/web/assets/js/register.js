const { createApp } = Vue

let app = createApp({
    data() {
        return {
            firstName: '',
            lastName: '',
            emailReg: '',
            passwordReg: '',
            dni: '',
            errorReg: '',
            email: '',
            password: '',
            error: ''

        }
    },
    created() {


    },

    methods: {
        login(email, password) {
            if (!email || !password) {
                this.error = "Please fill in all fields";
                return;
            }
            axios.post("/api/login?email=" + email + "&password=" + password)
                .then(response => {
                    console.log(response)
                    if (response.status.toString().startsWith('2')) {
                        axios.get("/api/clients/current")
                            .then(response2 => {
                                if (response2.data.role == "ADMIN") {
                                    window.location.href = "/web/assets/admin/overviewAdmin.html";
                                } else {
                                    window.location.href = "../pages/product.html";
                                }
                            })
                            .catch(error => console.log(error))
                    }

                    this.clearData();
                })
                .catch(error => {
                    this.error = error.response.data,
                        console.log(error)
                })
        },
        loginEvent() {
            this.login(this.email, this.password)
        },
        validatePassword(password) {
            const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

            return passwordRegex.test(password);
        },
        registerClient() {
            if (!this.firstName || !this.lastName || !this.emailReg || !this.passwordReg || !this.dni) {
                this.errorReg = "Please fill in all fields";
                return;
            }

            if (!this.validatePassword(this.passwordReg)) {
                this.errorReg = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character.";
                return;
            }
            axios.post('/api/clients', {
                "firstName": this.firstName,
                "lastName": this.lastName,
                "email": this.emailReg,
                "password": this.passwordReg,
                "dni": this.dni
            })
                .then(response => {
                    Swal.fire({
                        title: "Success",
                        text: "Registration completed successfully",
                        icon: "success"
                    });

                    setTimeout(() => {
                        this.login(this.emailReg, this.passwordReg)

                        this.clearData();
                    }, 2000);

                })
                .catch(
                    error => {
                        console.log(error)
                        this.errorReg = error.response.data
                    })
        },
        clearData() {
            this.firstName = '',
                this.lastName = '',
                this.emailReg = '',
                this.passwordReg = '',
                this.dni = '',
                this.errorReg = '',
                this.email = '',
                this.password = '',
                this.error = ''
        }
    }
}).mount('#app')
