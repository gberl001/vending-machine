         ___       ___       ___       ___       ___       ___       ___   
        /\  \     /\__\     /\  \     /\  \     /\  \     /\  \     /\  \  
       /::\  \   /:/ _/_   /::\  \   _\:\  \   /::\  \   /::\  \    \:\  \ 
      /\:\:\__\ /:/_/\__\ /::\:\__\ /\/::\__\ /::\:\__\ /:/\:\__\   /::\__\
      \:\:\/__/ \:\/:/  / \:\::/  / \::/\/__/ \:\:\/  / \:\ \/__/  /:/\/__/
       \::/  /   \::/  /   \::/  /   \/__/     \:\/  /   \:\__\    \/__/   
        \/__/     \/__/     \/__/               \/__/     \/__/            
                                  ___       ___   
                                 /\  \     /\  \  
                                 \:\  \   /::\  \ 
                                 /::\__\ /:/\:\__\
                                /:/\/__/ \:\/:/  /
                                \/__/     \::/  / 
                                           \/__/  
              ___       ___       ___       ___       ___       ___   
             /\  \     /\__\     /\  \     /\__\     /\  \     /\  \  
            /::\  \   /:/__/_   /::\  \   /:| _|_   /::\  \   /::\  \ 
           /:/\:\__\ /::\/\__\ /::\:\__\ /::|/\__\ /:/\:\__\ /::\:\__\
           \:\ \/__/ \/\::/  / \/\::/  / \/|::/  / \:\:\/__/ \:\:\/  /
            \:\__\     /:/  /    /:/  /    |:/  /   \::/  /   \:\/  / 
             \/__/     \/__/     \/__/     \/__/     \/__/     \/__/
README

Team: E
Name: Subject To Change
Members:
	Geoff Berl
	Ryan Castner
	Jimi Ford
	Dave Schoeffler


Install Instructions

	1) Extract Release2.zip folder
	2) Make sure all three jars are in same folder with database folder

Execute Instructions
	1) Double click desired jar file and you should be all set
	
	

*******************************************************************************
*******************************************************************************
************************ INVENTORY VIEW ***************************************
*******************************************************************************
*******************************************************************************

To show the real-time updates of our vending machine for prototyping purposes
we have implemented an Inventory View. This is a *representation* of the
physical machine that the restocker or the customer would see. 

It also performs as a way for managers to see the current contents of a machine.
This assumes that the restocker has properly stocked the machine, that is, if he 
said he stocked 4 bags of chips in slot 12 and actually put them in slot 15, 
this view will be incorrect now, but it is nothing that we can control unless 
there were sensors implemented in the vending machines to know if the restocker
made an error.

This view is not the most beautiful, as it is simply for simulation purposes
to represent what a customer or restocker would see if they were standing in
front of a machine. 

In this view, you may click on a slot to see the full contents of the slot.

Any image looking like a spring indicates that nothing is in that slot.

Please unless there are logical errors, be kind about the GUI look and feel,
it is just a simulation.

*******************************************************************************
*******************************************************************************
************************ MANAGER VIEW *****************************************
*******************************************************************************
*******************************************************************************

Analytics Tab:

	* To view all data, do not select a vending machine or click the show all
	  button found below the table of vending machines.
	* To view data for a particular vending machine, double click that machine 
	  in the table.  To reset the data back to show all data, click the show
	  all button found below the table of vending machines.
	* Additionally you may use the "Filter" field on the raw data tab to filter
	  results but please note, this does NOT affect the charts.

	* There is a raw data tab which is the default and then there is another
	  tab that shows a pie chart.  Note, this is unrelated to the search field.
	NOTE: Data is updated instantaneously so there is no need to refresh.


Restock Instructions Tab:

	* To add/edit restock instructions:
		1. Double click a Vending Machine from the table
		2. Drag and drop products from the products table into the restocking
		   list table
		3. Edit quantities (entering negative qtys for removal of products), 
		   locations, notes and suggested location costs as necessary by double 
		   clicking the appropriate table fields.
		4. Leave any general restocking notes in the notes field below the
		   restocking list table.
		5. Click the Confirm button (This must be clicked or the list will not
		   be saved, this is intended to allow a manager to decide not to
		   commit changes) 
	* There are three buttons below which can be utilized to simplify creating
	  restocking lists.  The New List button creates a blank list, completely
	  new.  The Reset List button unchecks all line items.  This can be useful
	  if items are restocked in the same locations and quanitites often.
	  Finally, there is a Confirm Changes button which is used to save changes.
	  Please note that New List and Reset List are changes and they will not 
	  take effect until the Confirm Changes button is clicked.
		Note: The notes field has a tooltip so if you need to see what it says
		just hold your mouse over the cell.


Manage Machines Tab:

	* To add a machine, enter the appropriate information into the fields,
	  select the desired dimensions and click the "Add" button
	* NOT IMPLEMENTED - To edit a machine, double click that machine in the 
	  table.
	* To remove a machine, right click the appropriate machine in the table and 
	  select "Remove" from the context menu
	  

Manage Products Tab:

	* To add a product, enter the appropriate information into the fields,
	  you may optionally include an image, and click the "Add/Update" button.
	  You may cancel at any time using the "Cancel" button.
	* To update a product, double click the product from the table of products
	  and the fields will be populated with the information.  Change the
	  information and/or picture as necessary and click the "Add/Update" button.
	  You may cancel at any time using the "Cancel" button.
	* To discontinue a product, you may enter the ten digit UPC in the field
	  to the right or you may simply right click the product in the table and
	  select "DISCONTINUE..." from the context menu.
	* To re-activate a product, be sure to click the button below the product
	  table to "Show All", find your desired product which will be highlighted 
	  in yellow, right click and select "REACTIVATE..." from the context menu.  
	  NOTE: Inactive products will not appear on the restocking list panel.
	* To recall a product, you may enter the ten digit UPC in the field to the 
	  right or you may simply right click the product in the table and
	  select "RECALL..." from the context menu.
	* To remove a recall on a product, be sure to click the button below the 
	  product table to "Show All", find your desired product which will be 
	  highlighted in red, right click and select "REMOVE RECALL..." from the 
	  context menu.  NOTE: Recalled products will not appear on the Restocking 
	  list panel and will not be able to vend.
	  
	  
*******************************************************************************
*******************************************************************************
************************ CUSTOMER VIEW ****************************************
*******************************************************************************
*******************************************************************************

Vending Machine Selection Panel
	* To select the desired Vending Machine, simply single click on the desired
	  row in the table. This will present you with two windows. 
	  	1) A graphical representation of the contents of the 
	  	   machine.
	  	2) A touch user interface which allows for the purchase of products.
	  	
Number Pad Panel
	* To select a product for purchase, start by entering the two or three digit 
	  slot location through the number pad and press enter. If the product is 
	  available for purchase(it is not expired or recalled), the product will 
	  show up as an item in the shopping cart on the right. You may continue 
	  adding items to your shopping cart by repeating the steps above.
	  
	* To purchase the items in your shopping cart, press the Checkout button. 
	  You will then see the items purchased removed from the Vending Machine
	  in the graphical view.
	  
	  NOTE: A any time you may select the clear order button in red and the 
	        items in your cart will removed and the total will be reset.
	        
	  NOTE: If you select an invalid location or a location that is empty an
	        appropriate message will be displayed on the number pad entry 
	        field.
	        
	* To simulate date, select the date chooser option in the lower left hand 
	  corner of the screen. Select the desired date and the internal system
	  date will be updated and products that are passed their expiration date
	  will be set to expired making them unavailable for purchase.
	  
	* To change your view to another Vending Machine, press the esc key from 
	  the Number Pad Panel and you will be returned to the Vending Machine 
	  Selection Panel.

IMPORTANT NOTE: If you are being told the machine is locked, this means that
the restocker is currently restocking the machine (you have started restocking
this machine and then tried to access it as a customer, something that is not
possible physically, so we stop you from doing thus in our simulation)
	* To unlock a machine the restocker must complete his restocking list 
	* Alternatively, a restocker can log into a machine and simply exit to 
	  unlock
	  
*******************************************************************************
*******************************************************************************
************************ RESTOCKER VIEW ***************************************
*******************************************************************************
*******************************************************************************
	  	
Machine List Screen
	* this will be the first screen on start-up
	* this list is synchronous across all managers and restockers based on the 
	  database
	1. Selecting a machine is as simple as clicking on it
		* remember this is a touchscreen interface
				
Restock Line Item Screen
	1. This provides an overview of the restocking instructions for a machine
		* If there is no restock list for a machine, the user can only return
		  to the list screen
		* If the restock list is already completed (signified by grayed out 
		  lines) user can still view the details of that restocking list
			- User can return by clicking Overview button
		* If there is a restock list to complete he can hit "Begin/Resume" 
		  button to begin/continue restocking
		
Restocker Detail Item Screen
	1. Provides a detailed view of each restock instruction
		* When the instructon is complete the restocker can hit "completed" 
		  to finish and advance
			- If the item was already completed he can only view that item 
			  and advance or go back with the arrow buttons '<' '>'
		* If the item is to be removed, it will tell them to remove, if the 
		  item is to be added, it will say add
			- The restocker can modify this field value if he does not have 
			   enough product to add, or can't remove that many products
		* The restocker can leave notes for each specific line item
			- The manager can view these notes from the restock tab (See 
			  Manager View -> Restocker Tab)
		* When all items are completed the application will notify the 
		  restocker and the user can return through the Overview button
			- He can then return to the machines list screen
		* Hitting the '<' or '>' will allow the restocker to move to different
		  line items and complete them in any order he wishes
		* Hitting the 'Overview' button will return the restocker to the list 
		  view

Notes:
	* The Manager only sees updates from the Restocker once ALL line items have
	  been completed
	* If a restocker can't complete an instruction he simply enters '0' for the
	  add/remove field to complete it
		- It is suggested that you leave a note for this line item for the 
		  Manager
	* You can use the inventory view to see changes made as restocking or 
	  removal occurs
	* When the restocker logs in the Manager and Customer are locked out from
	  accessing the machine
		- Finishing restocking unlocks the machine
		- Alternatively you may log into a machine and exit the restocker 
		  application to unlock a machine
		  
Know Bugs:
    * When the manager resets a completed restocking list, the check marks may
      not reset. This is only an issue on the front end as the table model 
      update automatically. The functionality still works fine.
      
Missing features:
    * We did not have time to implement an OUT OF ORDER mode which could be set 
      by the restocker.