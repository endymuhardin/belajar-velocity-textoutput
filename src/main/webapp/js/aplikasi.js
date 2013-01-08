HaloController = function($scope){
	$scope.email = "endy@artivisi.com"

	$scope.socialNetwork = [
		"Facebook",
		"Twitter",
		"Google+"
	];
}
PrintController = function($http, $scope, $window){
    $scope.printPreview = function(){
        console.log("Print Preview");
        $http.get('produk').success(function(data){
            $scope.printContent = data;
        });
    }
    $scope.printContent = "";
    $scope.print = function(){
        console.log("Cetak");
        var applet = $window.document.jzebra;
        if(applet == null){
            alert("Applet Gagal Load");
            return;
        }
        applet.findPrinter();
        applet.append(String.fromCharCode(15)); //condensed font
        
        // bold
        applet.append(String.fromCharCode(27)); 
        applet.append(String.fromCharCode(69)); 
        
        // print content
        applet.append($scope.printContent);
        
        // eject kertas / form feed
        applet.append("\f");
        applet.print();
    }
}