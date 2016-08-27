var shoppingCartApp = angular.module('shoppingCartApp', ['ngRoute']);

shoppingCartApp.config(['$routeProvider', function($routeProvider){
	$routeProvider
		.when('/', { // Only during first time loading
			templateUrl: '/shopping-cart-angular-cassandra/static/pages/content/displayCategories.html',
			controller: 'angular.shoppingcart.category.controller',
			resolve: {
				'categoryData': ['getGlobalCategoryListService', function(getCategoryList){
					return getCategoryList;
				}]
			}
		})
		.when('/showProducts', { // When coming back from addToCart
			templateUrl: '/shopping-cart-angular-cassandra/static/pages/content/displayCategories.html',
			controller: 'angular.shoppingcart.category.controller'
		})
		.when('/login', {
			templateUrl: '/shopping-cart-angular-cassandra/static/pages/content/login.html',
			controller: 'angular.shoppingcart.login.controller',
		})		
		.when('/viewProductDetails', {
			templateUrl: '/shopping-cart-angular-cassandra/static/pages/content/viewProductDetails.html',
			controller: 'angular.shoppingcart.selected.product.controller'
		})
		.when('/viewCart', {
			templateUrl: '/shopping-cart-angular-cassandra/static/pages/content/viewCart.html',
			controller: 'angular.shoppingcart.cart.controller'
		})
		.when('/viewProfile', {
			templateUrl: '/shopping-cart-angular-cassandra/static/pages/content/viewProfile.html',
			controller: 'angular.shoppingcart.customer.profile.controller'
		})
		.otherwise({
			redirectTo: '/'
		});
}])

shoppingCartApp.factory('customerInfoService', function(){
	var username = '';
	var firstname = '';
	var lastname = '';
	var customerInfo = {
		getUsername:function(){
			return username;
		},
		setUsername:function(data){
			username=data;
		},
		getFirstname:function(){
			return firstname;
		},
		setFirstname:function(data){
			firstname=data;
		},
		getLastname:function(){
			return lastname;
		},
		setLastname:function(data){
			lastname=data;
		}
	}
	return customerInfo;
});

/*shoppingCartApp.service('sessionValidityCheckService', ['$http', '$q', '$location', 'customerInfoService', function($http, $q, $location, custInfoSvc){
	var loginInfo = $q.defer();
	// Call display categories to retrieve list of categories from backend controller
	var config = {withCredentials:true};
	$http.get('/shopping-cart-angular/login.htm', config)
		.then(
			function(response) {
				alert(JSON.stringify(response));

				// Update global var customerInfoService
				custInfoSvc.setFirstname(response.data.firstName);
				custInfoSvc.setLastname(response.data.lastName);
				custInfoSvc.setUsername(response.data.username);
				
				$location.path('/displayCategories');
				
				loginInfo.resolve(response);
			}, 
			function(response) {
				alert(JSON.stringify(response));
				return loginInfo.reject(response);
			}
		);
	return loginInfo.promise;
}]);*/

shoppingCartApp.service('getGlobalCategoryListService', ['$http', '$q', '$location', 'catlistService', function($http, $q, $location, catlistService){
	var globalCategoryList = $q.defer();
	// Call display categories to retrieve list of categories from backend controller
	$http.get('/shopping-cart-angular-cassandra/displayCategories.htm')
		.then(
			function(response) {
				alert(JSON.stringify(response));

				// Update global var categoryList
				$.each(response.data.categories, function(key, value){
					catlistService.push(value);
				});
				globalCategoryList.resolve(response);
			}, 
			function(response) {
				alert(JSON.stringify(response));
				$location.path('/');
				return globalCategoryList;
			}
		);
	return globalCategoryList.promise;
}]);

shoppingCartApp.service('catlistService', function(){
	var catlist = [];
	return catlist;
});

shoppingCartApp.factory('itemDataFactory', function(){
	//var productData = {productData:''};
	return {productData:{}};
});

shoppingCartApp.factory('selectedProduct', function(){
	var product = {};
	return product;
});

shoppingCartApp.service('cartService', function(){
	//var productData = {productData:''};
	return cartCount;
});

shoppingCartApp.controller('angular.shoppingcart.navbar.controller', ['$scope', 'customerInfoService', function($scope, custInfoSvc){
	$scope.firstname = custInfoSvc.getFirstname();
	$scope.username = custInfoSvc.getUsername();
	$scope.numOfItems=0;
	// Update customer info in the nav-bar
	$scope.$on('customerInfoUpdateEvent', function(){
		$scope.firstname = custInfoSvc.getFirstname();
		$scope.username = custInfoSvc.getUsername();
	});
	// Update number of items in cart
	$scope.$on("cartItemAdditionEvent", function(event, args){
		$scope.numOfItems = args.numOfItems;
	});
	
	$scope.viewProfile = function(username){
		
	};
	
	$scope.viewCart = function(){
		
	};
}])

shoppingCartApp.controller('angular.shoppingcart.sidebar.controller', ['$scope','$location', function($scope, $location){
	$scope.login = function(){
		alert("Moving to login");
		$location.path("/login");
	}
	
	$scope.signup = function(){
		alert("SignUp up not implemented");
	}
	
	$scope.viewOrders = function(){
		alert("View Orders not implemented");
	}

	$scope.logout = function(){
		alert("Logout not implemented");
	}
}])

shoppingCartApp.controller('angular.shoppingcart.login.controller', ['$scope','customerInfoService','$location','$rootScope', '$http', function($scope, custInfoSvc, $location, $rootScope, $http){
	$scope.message = "";
	$scope.username = "";
	$scope.password = "";
	$scope.validateLogin = function(){
		var customer = {'username':$scope.username, 'password':$scope.password}
		var config = {headers:{'Content-Type':'application/json;charset=UTF-8', 'Accept':'application/json;charset=UTF-8', 'Cache-Control': 'no-cache'},
					  withCredentials:true};
		$http.post('/shopping-cart-angular-cassandra/validateLogin.htm', customer, config)
			.then(
				function(response){
					alert(JSON.stringify(response));
					// Update global var Customer
					custInfoSvc.setFirstname(response.data.firstname);
					custInfoSvc.setLastname(response.data.lastname);
					custInfoSvc.setUsername(response.data.username);
					
					// Broad cast the updated info so that nav-bar gets updated
					$rootScope.$broadcast('customerInfoUpdateEvent');
					
					alert($location.path());
					$location.path("/");
					alert($location.path());
					//$rootScope.$digest();
				},				  
				function(response){
					alert(JSON.stringify(response));
					$scope.message = "Invalid username or password!";  
				}
			);
	}
}])

shoppingCartApp.controller('angular.shoppingcart.category.controller', ['$scope', '$rootScope', '$http', 'catlistService', 'itemDataFactory', function($scope, $rootScope, $http, catlistService, itemDataFactory){
	//$scope.productMap = {};
	//alert(JSON.stringify(globalCategoryList)+"------ length"+globalCategoryList.length);
	$scope.message = "";
	$scope.$on("cartItemAdditionEvent", function(event, args){
		$scope.message = "Item "+args.item+" added to the cart";
	}); 
	$scope.categoryList = catlistService;
	$scope.showProducts = function(){
		alert($scope.categoryId);
		var request = {
			method:"GET",
			url:"/shopping-cart-angular-cassandra/retrieveProducts.htm?categoryId="+$scope.categoryId,
			headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8', 'Accept':'application/json;charset=UTF-8', 'Cache-Control': 'no-cache'}
		};		
		$http(request)
			.then(function(response){
				alert(JSON.stringify(response));

				// Update global var
				itemDataFactory.productData = response.data.productMap;

				// Iterate entire watch list for this scope to check if any of object has been updated
				//$rootScope.$digest();
				//alert(JSON.stringify(itemDataFactory.productData));
			},
			function(response){
				alert("Error : "+ response);
			});
		
		/*
		$.ajax({
			type:"GET",
			url:"/shopping-cart-angular/retrieveProducts.htm?categoryId="+$scope.categoryId,
			cache:false,
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			dataType:"json",
			success: function(data, status, xhr) {
				//alert("Status : "+ status + "----------"+ JSON.stringify(data))

				// Update global var
				itemDataFactory.productData = data.productMap;

				// Iterate entire watch list for this scope to check if any of object has been updated
				$rootScope.$digest();
				//alert(JSON.stringify(itemDataFactory.productData));
			},
			error: function(xhr, status) {
				alert("Error : "+ status + " : " + xhr.status);
			}
		});*/
	};
	/*
	$scope.$watchCollection(
		function(){ return categoryList; },
		function(newVal, oldVal){
			//Reset productDataMap
			//$scope.categoryList = [];

			// Then push new products
			$.each(newVal, function(key, productObj){
				$scope.categoryList.push(productObj);
			});
			//alert(JSON.stringify($scope.categoryList));
		});*/
}]);

shoppingCartApp.controller('angular.shoppingcart.item.list.controller', ['$scope', '$rootScope', 'itemDataFactory', 'selectedProduct', '$location', '$http', function($scope, $rootScope, itemDataFactory, selectedProduct, $location, $http){
	$scope.productDataList = [];
	$scope.chunkedProductDataList = [];
	$scope.showProducts = false;
	$scope.viewProductDetails=function(upc){
		var config = {method:'GET',
					  url:'/shopping-cart-angular-cassandra/getProductByUpc.htm?upc='+upc,
					  headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8', 'Accept':'application/json;charset=UTF-8', 'Cache-Control': 'no-cache'},
				  	  withCredentials:true};
		var url = '/shopping-cart-angular-cassandra/getProductByUpc.htm?upc='+upc;
		$http(config)
			.then(
				function(response){
					// Success
					alert("Success : " + JSON.stringify(response))
					selectedProduct.product = response.data
					alert(JSON.stringify(selectedProduct.product));
					$location.path("/viewProductDetails");
					alert($location.path());
					//$rootScope.$digest();
				}, 
				function(response){
					// Failure
					alert("Error : " + JSON.stringify(response));
				}
			);
	};

	$scope.$watchCollection(
		function(){ return itemDataFactory.productData; },
		function(newVal, oldVal){
			//Reset productDataMap
			$scope.productDataList = [];
			$scope.chunkedProductDataList = [];

			// Then push new products
			$.each(newVal, function(key, productObj){
				$scope.productDataList.push(productObj);
			});

			// Chunk the products into 3 columns
			$scope.chunkedProductDataList = chunk($scope.productDataList, 3)

			// Check length to determine whether to show the product table or not
			if($scope.productDataList.length > 0){
				$scope.showProducts = true
			}
			else{
				$scope.showProducts = false;
			}
    		//alert(JSON.stringify($scope.productDataMap));
	});
}]);

shoppingCartApp.controller('angular.shoppingcart.selected.product.controller', ['$scope', '$rootScope', 'selectedProduct', '$http', '$location', function($scope, $rootScope, selectedProduct, $http, $location){
	//$rootScope.$digest();
	$scope.productDetails = selectedProduct.product;
	$scope.qtyOrdered=0;

	$scope.addToCart = function(){
		alert($scope.productDetails.upc+", "+$scope.productDetails.title+", "+$scope.productDetails.price+", "+$scope.productDetails.inStockQty+", "+$scope.qtyOrdered);
		var cartItem = {"product":$scope.productDetails, "qtyOrdered":$scope.qtyOrdered}
		alert(JSON.stringify(cartItem));
		var config = {
					  method:"POST",
					  url:"/shopping-cart-angular-cassandra/addToCart.htm",
					  headers:{'Content-Type':'application/json;charset=UTF-8', 'Accept':'application/json;charset=UTF-8', 'Cache-Control': 'no-cache'},
					  data:cartItem
					 };
			
		$http(config)
			.then(
				function(response) {
					alert("Success : "+ JSON.stringify(response));
					var numOfItems = 0;
					$.each(response.data.items, function(key, item){
						numOfItems++;
					});
					var args = {'item':$scope.productDetails.title, 'numOfItems':numOfItems};
					$rootScope.$broadcast("cartItemAdditionEvent", args);
					$location.path("/showProducts");
				},
				function(response) {
					alert("Error : "+ JSON.stringify(response));
				}			
			)
			
	};

	$scope.$watchCollection(
		function(){ return selectedProduct.product; },
		function(newVal, oldVal){
			//Set new value to productDetails
			$scope.productDetails = newVal;
		});
}]);


shoppingCartApp.controller('angular.shoppingcart.cart.controller', ['$scope', function($scope){

	$scope.productId={};
	$scope.productName={};
	$scope.productQtyOrdered={};

	$scope.updateCartItem = function(productId){
		alert(productId+", "+$scope.productId[productId]+", "+$scope.productName[productId]+", "+$scope.productQtyOrdered[productId]);
		var product = {"id":productId, "name":$scope.productName[productId], "qtyOrdered":$scope.productQtyOrdered[productId]}
		$.ajax({
			type:"POST",
			url:"/shopping-cart-angular-cassandra/updateCart.htm",
			cache:false,
			contentType:"application/json;charset=UTF-8",
			dataType:"json",
			data: JSON.stringify(product),
			success: function(data, status, xhr) {
				alert("Status : "+ status+", Data : "+JSON.stringify(data));
			},
			error: function(xhr, status) {
				alert("Error : "+ status + " : " + xhr.status);
			}
		});
	}

	$scope.removeCartItem = function(productId){
		alert(productId+", "+$scope.productId[productId]+", "+$scope.productName[productId]+", "+$scope.productQtyOrdered[productId]);
		var product = {"id":productId, "qtyOrdered":0}
		$.ajax({
			type:"POST",
			url:"/shopping-cart/updateCart.htm",
			cache:false,
			contentType:"application/json;charset=UTF-8",
			dataType:"json",
			data: JSON.stringify(product),
			success: function(data, status, xhr) {
				alert("Status : "+ status+", Data : "+JSON.stringify(data));
				// Remove the cart item html and hide the div
				$("#cartItem_"+productId).html("");
				$("#cartItem_"+productId).hide();
			},
			error: function(xhr, status) {
				alert("Error : "+ status + " : " + xhr.status);
			}
		});
	}
}]);

shoppingCartApp.controller('angular.shoppingcart.customer.profile.controller', ['$scope', function($scope){
	
}]);

// Split the array into fixed number of columns
function chunk(arr, size) {
	var newArr = [];
	for (var i=0; i<arr.length; i+=size) {
		newArr.push(arr.slice(i, i+size));
	}
	return newArr;
}

// Ajax call to retrieve products
function updateCart(product){
	var productMap = {};


	return productMap;
}
