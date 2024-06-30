angular.module('voteApp', [])
    .controller('VoteController', function($http) {
        var vm = this;

        vm.options = [];
        vm.vote = null;
        vm.hostname = window.location.hostname;

        vm.getOptions = function() {
            $http.get('/api/votes/options').then(function(response) {
                var options = response.data;

                // Fetch flags for each option
                options.forEach(function(option) {
                    $http.get('https://restcountries.com/v3.1/name/' + option.country)
                        .then(function(flagResponse) {
                            option.flag = flagResponse.data[0].flags.png; // Assuming the country name is correct
                        });
                });

                vm.options = options;
            });
        };

        vm.submitVote = function() {
            $http.post('/api/votes', { vote: vm.vote }).then(function(response) {
                console.log(response.data);
            }, function(error) {
                console.error('Error: ' + error.data);
            });
        };

        vm.getOptions();
    });
