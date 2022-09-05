    $(document).ready(function() {
		$("#btnCancel").on("click", function(){
    		window.location = moduleUrl;
		});

		$("#fileImage").change(function(){
    		fileSize = this.files[0].size;
			//alert("File size: " + fileSize);

            if(fileSize > 102400){
    			this.setCustomValidity("You must choose an image less than 100KB");
				this.reportValidity();
        	}else{
				this.setCustomValidity("");
				showImageThumbnail(this);
			}

		});
	});

		function showImageThumbnail(fileInput){
			//alert('I am here');
			var file = fileInput.files[0];
			//console.log(file);
			var reader = new FileReader();

			reader.onload = function(e) {
				$("#thumbnail").attr("src", e.target.result);
			};
			console.log(reader);

			reader.readAsDataURL(file);
		}