define([ "jquery", 'bootstrap', "global", "formhelpers-phone" ], function($) {
	$(function() {
		// start after loading
		$(document).ready(function() {
			// start document ready
			
			$('#registration').on('focusout', 'input[name="email"],input[name="password"],input[name="phone"]', function() {
				var $this = $(this);
				$this.parent('.input-group').removeClass('has-error');
				var img = $($this.siblings('.aj-input-right')[0]);
				$.ajax({
					url : '/Application/check',
					data : {
						key : $this.attr('name'),
						value : $this.val()
					},
					loading : img,
					success : function(data) {
						if(data.ok){
							$this.parent('.input-group').addClass('has-success');
						}else{
							$this.parent('.input-group').addClass('has-error');
							console.log(data.message);
						}
					},
					error : function() {
						$this.parent('.input-group').addClass('has-error');
					}
				});
			});
			// end document ready
		});

		// end after loading
	});
});
