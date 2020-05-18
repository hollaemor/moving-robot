var app = new Vue({
    el: '#app',
    data: {
        instructions: [],
        direction: '',
        startingSteps: 0,
        movement: '',
        movementSteps: 0,
        errorMessage: '',
        spinner: false
    },
    computed: {
        noInstructions: function () {
            return this.instructions.length == 0 || !this.direction || !this.startingSteps;
        }
    },
    methods: {
        addMovement: function (event) {
            if (this.movement && this.movementSteps) {
                this.instructions.push({
                    id: this.instructions.length + 1,
                    move: this.movement,
                    frequency: this.movementSteps
                });
            }
        },
        clearSteps: function () {
            this.instructions = [];
            this.clear();
            this.direction = '';
            this.startingSteps = 0;
            this.movementSteps = 0;
            this.movement = '';
        },
        move: function () {
            this.clear();
            this.spinner = true;
            var payload = {
                start: {
                    direction: this.direction,
                    steps: this.startingSteps
                },
                instructions: this.instructions
            }
            axios.post('/orchestrate', payload).then(function (response) {
                var x = response.data.position.x;
                var y = response.data.position.y;
                document.getElementById('cell-' + y + '-' + x).innerHTML = app.getArrow(response.data.direction);

            }).catch(function (error) {
                if (error.response && error.response.data) {
                    app.errorMessage = error.response.data.message;
                } else {
                    app.errorMessage = error.toString();
                }
            }).then(function () {
                app.spinner = false;
            });
        },
        clear: function () {
            var cells = document.getElementsByClassName('cell-span');
            for (var i = 0; i < cells.length; i++) {
                cells[i].innerHTML = '&nbsp;';
            }
            this.errorMessage = '';
        },
        getArrow: function (direction) {
            switch (direction.toLowerCase()) {
                case "north":
                    return '<i class="fas fa-arrow-up"></i>';
                case "south":
                    return '<i class="fas fa-arrow-down"></i>';
                case "east":
                    return '<i class="fas fa-arrow-right"></i>';
                default:
                    return '<i class="fas fa-arrow-left"></i>';
            }
        }

    }
});