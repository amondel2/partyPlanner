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

function quickCounts(){
	$("#invitedCount").text($("div[guest]").length);
	$("#attendingCount").text($("div[attending]").length);
	$.ajax({
		url: baseDir + "/TableConf/getAddressCount",
		type: 'GET',
		cache: false,
		success: function(data){
			$("#uniqueCount").text(data.Count);
		},
		error: function(){
			alert("failBoat")
		}
	});
	$.ajax({
		url: baseDir + "/TableConf/getGuestCount",
		type: 'GET',
		cache: false,
		success: function(data){
			$("#guestCount").text(data.Count);
		},
		error: function(){
			alert("failBoat")
		}
	});
	
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
			if($("#fiterAttend").attr("attrib") == 'on') {
				showAttending();
			}
			quickCounts();
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
			var maxRight = pos.left + $("#tableContainer").width() - $(this).outerWidth();
			var maxBottom = pos.top + $("#tableContainer").height() - $(this).outerHeight();
			if(ui.offset.left < pos.left) {
				$(this).offset({ top: ui.offset.top, left: pos.left });
			} else if( ui.offset.left > maxRight) {
				$(this).offset({ top: ui.offset.top, left: maxRight });
			}	
			if(ui.offset.top < pos.top) {
				$(this).offset({ top: pos.top, left: $(this).offset().left });
			} else if( ui.offset.top > maxBottom) {
				$(this).offset({ top: maxBottom, left: $(this).offset().left });
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
	
	 $( "#partyPlanMenu button" ).button({
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
	 
	 $("#partyPlanMenu").menu({ position: { at: "left bottom" },  icons: { submenu: "ui-icon-triangle-1-s"} });
	
	 quickCounts();
	
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
	
	$(document.body).on("click","[title='Edit Table']",function(){
		$("#editDialTableId").val($(this).attr('table'));
		$("#editDialTableName").val($(this).parent().parent().children().first().text());
		$("#editTableForm").dialog('open');
	});
	
	$(document.body).on("click","[title='Edit User']",function(){
		var guestId = $(this).attr('user');
		var name = $(this).parent().parent().text();
		$.ajax({
			url: baseDir + "/partyGuest/edit/" + guestId,
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
				$("#editGuest #party").parent().remove();
				$("#editGuest #guest").replaceWith("<span>" + $.trim(name)  +"</span>");
				$("#editGuest").dialog({
					autoOpen: true,
					height: 700,
					width: 700,
					minWidth: 600,
					minHeight: 700,
					buttons: {
					 "Save": function() {
						 $.ajax({
								url: baseDir + "/TableConf/editUser",
								type: 'POST',
								cache: false,
								data: $("#editGuest form").serialize(),
								success: function(data){
									if($("#guest_id_" + guestId).parent().prop('id') == 'guestList') {
										sortList();
									} else {
										$("#guest_id_" + guestId).replaceWith(data);
										$("#guest_id_" + guestId).draggable(guestDrag);
										quickCounts();
									} 
									$("#editGuest").dialog( "close" );
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
			url: baseDir + "/partyGuest/create/",
			type: 'GET',
			cache: true,
			success: function(data){
				if($("#addGuestDi").children().length > 0) {
					$("#addGuestDi").dialog( "destroy" );
					$("#addGuestDi").empty();
				}
				$("#addGuestDi").append($(data).find("form"));
				$("#addGuestDi fieldset.buttons").remove();
				$("#addGuestDi #seat").parent().remove();
				$("#addGuestDi label[for='partyGuests']").parent().remove();
				$("#addGuestDi #party").parent().remove();
				$.each($("#tableContainer").children().find(".guest").add($("#guestList").children()),function(index,value) {
					var guestId = $(value).attr('relgid');
					$("#addGuestDi #guest option[value='"+guestId+"']").remove();
				});
				$("#addGuestDi").dialog({
					autoOpen: true,
					height: 700,
					width: 700,
					minWidth: 600,
					minHeight: 700,
					buttons: {
					 "Save": function() {
						 
						 $.ajax({
								url: baseDir + "/TableConf/saveUser",
								type: 'POST',
								cache: false,
								data: $("#addGuestDi form").serialize(),
								success: function(data){
									if(data.status == "SUCCESS") {
										sortList();
										$("#addGuestDi").dialog( "close" );
									} else {
										alert(data.msg)
									}
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
					 close: function() {
						 $("#addGuestDi input").val("");
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
						$("#guest_id_" + delGuestId).remove();
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
	
	$("#editTableForm").dialog({
		autoOpen: false,
		height: 200,
		minHeight: 200,
		buttons: {
		 "Edit Table": function() {
			 var tid = $("#editDialTableId").val();
			 var tn = $.trim($("#editDialTableName").val());
			 if(tn && tid && tn.length > 0) {
				 $.ajax({
					 	cache: false,
						url: baseDir + "/TableConf/editTableName",
						type: 'POST',
						data: {"tableName":tn,"tableId":tid},
						success: function(data){
							if(data == "success") {
								$("#table_" + tid +" .header:first > div:first").html(tn);
								$("#table_" + tid +" .header:first > div:first").attr("title",tn);
							}
							$("#editTableForm").dialog( "close" );
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
			 $("#editDialTableName").val("");
			 $("#editDialTableId").val("");
			 },
		 modal: true
	});
	
	$("#addTableForm").dialog({
		autoOpen: false,
		height: 200,
		minHeight: 200,
		buttons: {
		 "Create Table": function() {
			 var tn = $.trim($("#tableName").val());
			 if(tn && tn.length > 0) {
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
		if ($(this).attr("attrib") == 'off') {
			$(this).attr("attrib","on");
			$(this).text("Show All Guest");
			showAttending();
	    } else {
	    	$(this).attr("attrib","off");
	    	$(this).text("Show Only Attending Guest");
	       showAllGuest();
	    }
	});
	
	
	
	
	$("#sortList").on("click",sortList);
});