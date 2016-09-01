  $(document).ready(function() {
    

    /* initialize the external events
    -----------------------------------------------------------------*/
    $('#external-events .fc-event').each(function() {

      // store data so the calendar knows to render an event upon drop
      $(this).data('event', {
        title: $.trim($(this).text()), // use the element's text as the event title
        stick: true, // maintain when user navigates (see docs on the renderEvent method)
        color: '#00bcd4'
      });

      // make the event draggable using jQuery UI
      $(this).draggable({
        zIndex: 999,
        revert: true,      // will cause the event to go back to its
        revertDuration: 0  //  original position after the drag
      });

    });


    /* initialize the calendar
    -----------------------------------------------------------------*/
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();

	if(dd<10) {
		dd='0'+dd
	} 

	if(mm<10) {
		mm='0'+mm
	} 

	today = mm+'-'+dd+'-'+yyyy;

    $('#calendar').fullCalendar({
		dayClick: function(date, allDay, jsEvent, view) {

        //alert('Clicked on: ' + date.format());
		
		//$('#calendar').fullCalendar('renderEvent', { title: 'YOUR TITLE', start: date, allDay: true }, true );
		
		console.log(date);

    },
	eventClick: function(calEvent, jsEvent, view) {

        alert('Event: ' + calEvent.title);
      
    },
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,basicWeek,basicDay'
      },
      defaultDate: today,
      editable: false,	// don't edit
      droppable: false, // this allows things to be dropped onto the calendar
      eventLimit: true, // allow "more" link when too many events
      events: [
        {
          title: 'All Day Event',
          start: '2015-05-01',
          color: '#9c27b0'
        },
        {
          title: 'Long Event',
          start: '2015-05-07',
          end: '2015-05-10',
          color: '#e91e63'
        },
        {
          id: 999,
          title: 'Repeating Event',
          start: '2015-05-09T16:00:00',
          color: '#ff1744'
        },
        {
          id: 999,
          title: 'Repeating Event',
          start: '2015-05-16T16:00:00',
          color: '#aa00ff'
        },
        {
          title: 'Conference',
          start: '2015-05-3',
          end: '2015-05-5',
          color: '#01579b'
        },
        {
          title: 'Meeting',
          start: '2015-05-12T10:30:00',
          end: '2015-05-12T12:30:00',
          color: '#2196f3'
        },
        {
          title: 'Lunch',
          start: '2015-05-12T12:00:00',
          color: '#ff5722'
        },
        {
          title: 'Meeting',
          start: '2015-05-12T14:30:00',
          color: '#4caf50'
        },
        {
          title: 'Happy Hour',
          start: '2015-05-12T17:30:00',
          color: '#03a9f4'
        },
        {
          title: 'Dinner',
          start: '2015-05-12T20:00:00',
          color: '#009688'
        },
        {
          title: 'Birthday Party',
          start: '2015-05-13T07:00:00',
          color: '#00bcd4'
        },
        {
          title: 'Birthday Party',
          start: '2016-07-17T07:00:00',
          color: '#00bcd4'
        }
      ]
    });
	

	

    
  });