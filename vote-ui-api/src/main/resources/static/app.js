angular.module('voteApp', [])
    .controller('VoteController', function($http) {
        var vm = this;

        vm.options = {};
        vm.vote = null;
        vm.hostname = window.location.hostname;

        vm.getOptions = function() {
            $http.get('/api/vote/options').then(function(response) {
                vm.options = response.data;
            });
        };

        vm.submitVote = function() {
            $http.post('/api/vote/', { vote: vm.vote }).then(function(response) {
                alert(response.data);
            }, function(error) {
                alert('Error: ' + error.data);
            });
        };

        vm.getOptions();
    });
