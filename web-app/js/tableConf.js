var seatDropable = {
        accept: function(dropClass){
        	try{
        		var childCheck = ( $(this).children('.guest').length == 0 || ( $(this).children('.guest').length == 1 && $(this).children('.guest').prop('id') == dropClass.prop('id')));
        		var success = childCheck && $(dropClass).hasClass('guest');
        		return success;
        	} catch(e) {
        		return false;
        	}
        },
        tolerance: "touch",
        greedy: true,
        drop: function( event, ui ) {
        	$(ui.draggable).data("success",true);
        	$(ui.draggable).appendTo( this );
        	var seatId = $(this).attr('seat');
        	var guestId = $(ui.draggable).attr('guest');
    		$.ajax({
    			url: baseDir + "/TableConf/sitDown",
    			type: 'POST',
    			cache: false,
    			data: {"seatId":seatId,'guestId':guestId},
    			error: function(){
    				alert("failBoat")
    			}
    		});
        }
    };


function showAttending(){
	$("#guestList > div").css("display","none");
	$("#guestList > div[attending='attend']").css("display","block");
}
function showAllGuest(){
	$("#guestList > div").css("display","block");
}

var sortList = function(){
	$.ajax({
		url: baseDir + "/TableConf/getListSort",
		type: 'GET',
		cache: false,
		success: function(data){
			var scrolTop = $("#guestList").scrollTop();
			$("#guestList").empty();
			$("#guestList").append(data);
			$("#guestList .guest").draggable(guestDrag);
			if($("#fiterAttend").is(":checked")) {
				showAttending();
			}
			$("#guestList").scrollTop(scrolTop);
		},
		error: function(){
			alert("failBoat")
		}
	});
	
};

var delTableId,delSeatId,delGuestId;

var tableDrag = { 
		containment: $("#tableContainer"),
		stack: "#guestTableConfContainer",
		scroll: false,
		stop: function( event, ui ) {
			var pos = $("#tableContainer").offset();
			if(ui.offset.left < pos.left) {
				$(this).offset({ top: ui.offset.top, left: pos.left });
			}
			if(ui.offset.top < pos.top) {
				$(this).offset({ top: pos.top, left: $(this).offset().left });
			}
			var tableId = $(this).attr('table');
			$.ajax({
				url: baseDir + "/TableConf/tableDrop",
    			type: 'POST',
    			cache: false,
    			data: {"tableId":tableId,"top":$(this).offset().top,"left":$(this).offset().left},
    			error: function(){
    				alert("failBoat")
    			}
			});
		}
		
	};


var guestDrag= { 
		containment: $("#tableContainer"),
		stack: "#guestTableConfContainer",
		revert: "invalid",
		zIndex: 1,
		helper: "clone",
		scroll: true,
		start: function(){
			$(this).data("success",false);
			$(this).css("display","none");
		},
		stop: function( event, ui ) {
			$(this).css("display","block");
			if(!$(this).data("success") && $(this).parent().prop('id') != 'guestList'){
	        	var guestId = $(this).attr('guest');
	        	var elm = $(this)
	        	elm.css("display","none");
	    		$.ajax({
	    			url: baseDir + "/TableConf/standUp",
	    			type: 'POST',
	    			cache: false,
	    			data: {'guestId':guestId},
	    			success: function() {
	    				elm.remove();
	    				sortList();
	    			},
	    			error: function(){
	    				elm.css("display","block");
	    				alert("failBoat")
	    			}
	    		});
			}
		}
	};

$(document).ready(function(){
	
	 $( ".fieldSetButtons button" ).button({
		 icons: {
		 primary: "ui-icon-plus "
		 }
	 });
	 
	 $( document ).tooltip({
		 items: ".guest[title]",
		 content: function() {
			 var element = $( this );
			 if ( element.is( "[title]" ) ) {
				 return element.attr( "title" );	
			 }
		 }
	});
	
	
	$(".guest").draggable(guestDrag);
	$(".table").draggable(tableDrag);
	$(".seat").droppable(seatDropable);
	
	$(document.body).on("click",".seatAdder",function(){
		var tableId = $(this).attr('table');
		$.ajax({
			url: baseDir + "/TableConf/addSeat",
			type: 'POST',
			cache: false,
			data: {"tableId":tableId},
			success: function(data){
				$("#table_" + tableId).append(data);
				$(".seat").droppable(seatDropable);
			},
			error: function(){
				alert("failBoat")
			}
		});
	});
	
	
	$(document.body).on("click","[title='Delete Table']",function(){
		delTableId = $(this).attr('table');
		$("#tableDrop").dialog('open');
	});
	
	$(document.body).on("click","[title='Delete Seat']",function(){
		delSeatId = $(this).attr('seat');
		$("#seatDrop").dialog('open');
		
	});
	
	$(document.body).on("click","[title='Delete User']",function(){
		delGuestId = $(this).attr('user');
		$("#guestDrop").dialog('open');
	});
	
	$(document.body).on("click","[title='Edit User']",function(){
		var guestId = $(this).attr('user');
		$.ajax({
			url: baseDir + "/guest/edit/" + guestId,
			type: 'GET',
			cache: false,
			success: function(data){
				if($("#editGuest").children().length > 0) {
					$("#editGuest").dialog( "destroy" );
					$("#editGuest").empty();
				}
				$("#editGuest").append($(data).find("form"));
				$("#editGuest fieldset.buttons").remove();
				$("#editGuest #seat").parent().remove();
				$("#editGuest").dialog({
					autoOpen: true,
					height: 700,
					width: 700,
					minWidth: 600,
					minHeight: 700,
					buttons: {
					 "Save": function() {
						 var fn = $("#editGuest #firstName").val();
						 var ln = $("#editGuest #lastName").val();
						 if(fn && ln ) {
						 $.ajax({
								url: baseDir + "/TableConf/editUser",
								type: 'POST',
								cache: false,
								data: $("#editGuest form").serialize(),
								success: function(data){
									if($("#guset_id_" + guestId).parent().prop('id') == 'guestList') {
										sortList();
									} else {
										$("#guset_id_" + guestId).replaceWith(data);
										$("#guset_id_" + guestId).draggable(guestDrag);
									} 
									$("#editGuest").dialog( "close" );
								},
								error: function(){
									alert("failBoat")
								}
							});
						 } else {
							 alert("Enter a name");
						 }
					 	},
					 	Cancel: function() {
						 $( this ).dialog( "close" );
					 	}
					 },
					 close: function() {
						 $("#editGuest input").val("");
						 },
					 modal: true
				});
				
			},
			error: function(){
				alert("failBoat")
			}
		});
	});
	
	$("#addGuest").on("click",function(){
		$.ajax({
			url: baseDir + "/guest/create/",
			type: 'GET',
			cache: true,
			success: function(data){
				if($("#editGuest").children().length > 0) {
					$("#editGuest").dialog( "destroy" );
					$("#editGuest").empty();
				}
				$("#editGuest").append($(data).find("form"));
				$("#editGuest fieldset.buttons").remove();
				$("#editGuest #seat").parent().remove();
				$("#editGuest").dialog({
					autoOpen: true,
					height: 700,
					width: 700,
					minWidth: 600,
					minHeight: 700,
					buttons: {
					 "Save": function() {
						 var fn = $("#editGuest #firstName").val();
						 var ln = $("#editGuest #lastName").val();
						 if(fn && ln ) {
						 $.ajax({
								url: baseDir + "/TableConf/saveUser",
								type: 'POST',
								cache: false,
								data: $("#editGuest form").serialize(),
								success: function(data){
									sortList();
									$("#editGuest").dialog( "close" );
								},
								error: function(){
									alert("failBoat")
								}
							});
						 } else {
							 alert("Enter a name");
						 }
					 	},
					 	Cancel: function() {
						 $( this ).dialog( "close" );
					 	}
					 },
					 close: function() {
						 $("#editGuest input").val("");
						 },
					 modal: true
				});
			}
		});
	});
	
	$("#addTable").on("click",function(){
		$( "#addTableForm" ).dialog( "open" );
	});
	
	
	$("#tableDrop").dialog({
		autoOpen: false,
		height: 200,
		minHeight: 200,
		buttons: {
		 "Ok": function() {
			 $.ajax({
				 	cache: false,
					url: baseDir + "/TableConf/delTable",
					type: 'POST',
					data: {"tableId":delTableId},
					success: function(data){
						$("#table_" + delTableId).remove();
						delTableId = null;
						sortList();
						$("#tableDrop").dialog('close');
					},
					error: function(){
						alert("failBoat")
					}
				});
		 	},
		 	Cancel: function() {
			 $( this ).dialog( "close" );
		 	}
		 },
		 modal: true
	});
	
	$("#seatDrop").dialog({
		autoOpen: false,
		height: 200,
		minHeight: 200,
		buttons: {
		 "Ok": function() {
			 $.ajax({
					url: baseDir + "/TableConf/delSeat",
					type: 'POST',
					cache: false,
					data: {"seatId":delSeatId},
					success: function(data){
						$("#seat_" + delSeatId).remove();
						delSeatId=null
						sortList();
						$("#seatDrop").dialog('close');
					},
					error: function(){
						alert("failBoat")
					}
				});
		 	},
		 	Cancel: function() {
			 $( this ).dialog( "close" );
		 	}
		 },
		 modal: true
	});
	
	$("#guestDrop").dialog({
		autoOpen: false,
		height: 200,
		minHeight: 200,
		buttons: {
		 "Ok": function() {
			 $.ajax({
					url: baseDir + "/TableConf/delGuest",
					type: 'POST',
					cache: false,
					data: {"guestId":delGuestId},
					success: function(data){
						$("#guset_id_" + delGuestId).remove();
						delGuestId = null
						$("#guestDrop").dialog('close');
						sortList();
					},
					error: function(){
						alert("failBoat")
					}
				});
		 	},
		 	Cancel: function() {
			 $( this ).dialog( "close" );
		 	}
		 },
		 modal: true
	});
	
	
	$("#addTableForm").dialog({
		autoOpen: false,
		height: 200,
		minHeight: 200,
		buttons: {
		 "Create Table": function() {
			 var tn = $("#tableName").val();
			 if(tn) {
			 $.ajax({
				 	cache: false,
					url: baseDir + "/TableConf/addTable",
					type: 'POST',
					data: {"tableName":tn},
					success: function(data){
						$("#tableContainer").append(data);
						$(".table").draggable(tableDrag);
						$(".seat").droppable(seatDropable);
						$("#addTableForm").dialog( "close" );
					},
					error: function(){
						alert("failBoat")
					}
				});
			 } else {
				 alert("Enter a name");
			 }
		 	},
		 	Cancel: function() {
			 $( this ).dialog( "close" );
		 	}
		 },
		 close: function() {
			 $("#tableName").val("");
			 },
		 modal: true
	});
	
	$.each($("[top][top != ''][left][left != '']"),function(i,v) {
		$(v).offset({top:$(v).attr('top'),left:$(v).attr('left')});
	});
	
	
	$("#fiterAttend").on("click",function(){
		if ($(this).is(':checked')) {
			showAttending();
	    } else {
	       showAllGuest();
	    }
	});
	
	
	$("#sortList").on("click",sortList);
});