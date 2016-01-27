/* js/fileAppControllers.js */

function fileCtrl ($scope, $http,$cookies) {
    $scope.partialDownloadLink = 'http://localhost:8080/api/file/download?filename=';
    $scope.filename = '';

    $scope.uploadFile = function() {
        $scope.processDropzone();
    };
    
    $scope.reloadFiles = function() {
    	loadFiles();
    }

    $scope.reset = function() {
        $scope.resetDropzone();
    };
    
    var loadFiles = function() {
    	$http.get('api/file/files').success(function(data) {
    		$scope.uploadedFiles = data;
		})
    }
    
    $scope.deleteFile = function(filename) {
        $http({ url: 'api/file/delete/' + encodeURIComponent(filename), 
                method: 'DELETE' 
        }).then(function(res) {
            console.log(res.data);
            loadFiles();
        }, function(error) {
            console.log(error);
        });
    };
  
    
    loadFiles();
    
}
angular.module('app').controller('fileCtrl', fileCtrl);