angular.module('voteApp', [])
    .controller('VoteController', function($http) {
        var vm = this;

        vm.options = [];
        vm.vote = null;
        vm.name = null;
        vm.hostname = window.location.hostname;

        vm.getOptions = function() {
            $http.get('/api/votes/options').then(function(response) {
                // Check if the response data is an array
                if (Array.isArray(response.data)) {
                    var options = response.data;

                    // Fetch flags for each option
                    options.forEach(function(option) {
                        if (option.country) {
                            $http.get('https://restcountries.com/v3.1/name/' + option.country)
                                .then(function(flagResponse) {
                                    option.flag = flagResponse.data[0].flags.png; // Assuming the country name is correct
                                }).catch(function(error) {
                                    console.error('Error fetching flag for', option.country, error);
                                });
                        } else {
                            console.warn('No country specified for option', option);
                        }
                    });

                    vm.options = options;
                } else {
                    console.error('Expected an array of options but received:', response.data);
                }
            }).catch(function(error) {
                console.error('Error fetching options:', error);
            });
        };

        vm.submitVote = function() {
            $http.post('/api/votes', { vote: vm.vote, name: vm.name }).then(function(response) {
                console.log(response.data);
            }, function(error) {
                console.error('Error:', error.data);
            });
        };

        vm.getOptions();
    });
