 $(document).ready(function(){
  		 function parseFn(data) {     
    	         var rows = [];      
    	         for(var i=0; i<data.length; i++){  
    	            rows[rows.length] = {      
    	                data:data[i], 
    	                value:data[i].name, 
    	                result:data[i].name
    	            };       
    	          }      
    	         return rows;      
    	    } 
   
    	  $("#kw").autocomplete("suggest.php", {
    	    minChars: 1,
    	    width: 419,
    	    matchContains: "word", 
    	    autoFill: false,
    	    dataType: 'json',
    	    parse: parseFn,
    	    formatItem: function(row, i, max) {   
				 var item =row.name;   
    			 return item;
    	    } 
    	   }).result(function(event, row, formatted) {
					 document.f.submit();
            }); 
   })   