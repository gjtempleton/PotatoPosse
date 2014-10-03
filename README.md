PotatoPosse
===========
<h2>Activities</h2>
<ul>
	<li>
		MainActivity
		<ul>
			<li>
				Purpose: starting activity, main screen to scroll through problem entries and access other activities
			</li>
			<li>
				Functionality:
				<ol>
					<li>Update the dataset (requires internet connection)</li>
					<li>Traverse through problem entries</li>
					<li>Open a problem breakdown</li>
					<li>Open leaf problem browser</li>
					<li>Open pest problem browser</li>
					<li>Open tuber problem browser</li>
				</ol>
			</li>
			<li>
				Methods:
				<ul>
					<li>firstTimeSetup: called during first run of application to set up dataset storage locations</li>
					<li>setupNavBar: sets up the navigation bar situated at the top of the screen</li>
					<li>setupTabs: sets up the three category tabs situated at the bottom of the screen</li>
					<li>setTabColors: used to color the tabs, depending on the selected tab</li>
					<li>updateFiles: updates the local dataset from server</li>
				</ul>
			</li>
		</ul>
	</li>
	<li>
		SendEmailActivity
		<ul>
			<li>
				Purpose: to send an email to a contact (or other) containing a photo of current problem
			</li>
			<li>
				Functionality:
				<ol>
					<li>Search through contact list for recipient</li>
					<li>Select a contact from list</li>
					<li>Manually enter email address of recipient</li>
					<li>Send email with image attachment</li>
				</ol>
			</li>
			<li>
				Methods:
				<ul>
					<li>onCreate: sets up contact list</li>
					<li>setupView: sets up alert dialog for manual email address entry</li>
					<li>sendEmail: set up email outline, attach image and prompt to open email client</li>
					<li>getNameEmailDetails: get the names and email addresses of contacts</li>
				</ul>
			</li>  
		</ul>
	</li>
	<li>
		SummaryActivity
		<ul>
			<li>
				Purpose: to view a full breakdown of a selected problem
			</li>
			<li>
				Functionality:
				<ol>
					<li>Traverse through carousel of problem images and open individuals in fullscreen</li>
					<li>Traverse through problem breakdown (description, test, control)</li>
					<li>Open TestActivity</li>
					<li>Open EmailActivity</li>
				</ol>
			</li>
			<li>
				Methods:
				<ul>
					<li>setupVariables: sets up data for current problem view</li>
					<li>setupView: sets up view for the activity</li>
					<li>getCategoryText: get the icons for the category type(s)</li>
					<li>getTestIdList: return an array list of test id integers</li>
				</ul>
			</li>
		</ul>
	</li>
	<li>
		SymptomListActivity
		<ul>
			<li>
				Purpose: to view populate the problem browser list view
			</li>
			<li>
				Functionality:
				<ol>
					<li>Create a clickable list view item, including thumbnail and problem name</li>
				</ol>
			</li>
			<li>
				Classes:
				<ul>
					<li>ThumbnailAdapter: creates list asynchronously due to slow thumbnail creation</li>
				</ul>
			</li>
			<li>
				Methods:
				<ul>
					<li>onCreate: sets ThumbnailAdapter as list adapter</li>
					<li>getView: returns a view object for the problem browser list</li>
				</ul>
			</li>
		</ul>
	</li>
	<li>
		TakePictureActivity
		<ul>
			<li>
				Purpose: to view populate the problem browser list view
			</li>
			<li>
				Functionality:
				<ol>
					<li>Take a photo using the camera application</li>
					<li>Allow user to view taken photo</li>
					<li>Allow user to accept or reject the photo taken before choosing recipient</li>
				</ol>
			</li>
			<li>
				Methods:
				<ul>
					<li>getOutputPhotoFile: return the location of where the photo should be output to</li>
					<li>onActivityResult: used to handle user decision of accepting or rejecting</li>
					<li>showPhoto: show taken image</li>
				</ul>
			</li>
		</ul>
	</li>
	<li>
		TestActivity
		<ul>
			<li>
				Purpose: to view breakdown of test information
			</li>
			<li>
				Functionality:
				<ol>
					<li>Traverse through carousel of test images</li>
					<li>Open and play the original test video (requires internet connection)</li>
				</ol>
			</li>
			<li>
				Methods:
				<ul>
					<li>setupVariables: sets up data for current test view</li>
					<li>setupView: sets up view for the activity</li>
					<li>sortImages: sort the 2D array (using the position indexes) to ensure test carousel images are in the correct order</li>
				</ul>
			</li>
		</ul>
	</li>
	<li>
		ZoomImageActivity
		<ul>
			<li>
				Purpose: to view an image in fullscreen
			</li>
			<li>
				Functionality:
				<ol>
					<li>Open an image in fullscreen to allow enlarged view</li>
				</ol>
			</li>
			<li>
				Methods:
				<ul>
					<li>onCreate: open the parameter image in a fullscreen ImageView</li>
				</ul>
			</li>
		</ul>
	</li>
</ul>
