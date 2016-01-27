/* js/fileAppDirectives */
function dropzone($cookies) {
	
    return function(scope, element, attrs) {
    	
        var config = {
            url: 'http://localhost:8080/api/file/upload',
            maxFilesize: 1000,
            paramName: "uploadfile",
            maxThumbnailFilesize: 10,
            parallelUploads: 1,
            autoProcessQueue: false
        };

        var eventHandlers = {
            'addedfile': function(file) {
                scope.file = file;
                if (this.files[1]!=null) {
                    this.removeFile(this.files[0]);
                }
                scope.$apply(function() {
                    scope.fileAdded = true;
                });
            },

            'complete': function (file, response) {
            	scope.reloadFiles();
            	dropzone.removeAllFiles();
            }
        };

        dropzone = new Dropzone(element[0], config);
        
        dropzone.on("sending", function(file, xhr, data) {
        });
        
        angular.forEach(eventHandlers, function(handler, event) {
            dropzone.on(event, handler);
        });

        scope.processDropzone = function() {
            dropzone.processQueue();
        };

        scope.resetDropzone = function() {
            dropzone.removeAllFiles();
        }
        
    }
}
angular.module('app').directive('dropzone', dropzone);


