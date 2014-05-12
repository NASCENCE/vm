/**
include "shared.thrift"
*/
namespace cpp emInterfaces
namespace csharp emInterfaces


enum emExceptionType
{
	/** Serious error, needs handling or recovery */
	CRITICAL = 0,
	/** Treat as a warning */
	NONFATAL = 1
}

/**
Structure to encapsulate log setting
*/
struct emLogServerSettings
{

	/** URL for log server */
	1: string	logServer,
	
	/** Name of experiment */
	2: string	experimentName,
}

/**
	Standard types of waveform that the signal generator can produce
*/
enum emLogEventType
{
	/** Undefined, here for consistency */
	emNULL = 0,
	CONFIG = 1,
	emERROR = 2,
	MISC = 3,
	COMMAND = 4,
	RESPONSE = 5,
	DONOTLOG = 6,
	DISPLAYASMESSAGEBOX = 7
}

/**
The device directly controlling the EM should make sure sensible things are happening, and if not throw an exception.  All methods should return problems via exceptions.  Exceptions should contain as much information as possible.  Exceptions can be fatal or non fatal. On a fatal exception, the controlling program should probably abandon what it is doing and reset.
*/		  
exception emException{
	/** Error codes, to be defined in documentation */
	1: i32 errorCode,
	/** Why the error was thrown	 */
	2: string Reason,  
	/** Where (e.g. module, line number) the error came from */
	3: string Source,  
	/** What type of error this is */
	4: emExceptionType exceptionType  
}



/**
The EM controller plays back a set of operations. Think of it as a multitrack audio editing software. Logcally, each PIN has its own queue of operations. Each of these operations has a start and end time, and a set of parameters to define what each pin will be doing during that time. Operations are one of a few basic types:
*/
enum emSequenceOperationType
{
	/** Undefined, but here just to make sure there is a null type */
	emNULL = 0,
	/** Play back from buffer */
	ARBITRARY = 1,
	/** record from a pin and store into a buffer. */
	RECORD = 2,
	/** does nothing for a period of time. Used to pad operations */
	WAIT = 3,
	/** Use the waveFormType for signal generation */
	PREDEFINED = 4,
	/** Outputs a value of amplitude 	*/
	CONSTANT = 5,
	/** Outputs a Binary value on based on amplitude. Amplitude=0, output 0, else 1 */
	DIGITAL = 6,
	/** Outputs a value based on contents of register 	*/
	CONSTANT_FROM_REGISTER = 7,
}

/**
	Standard types of waveform that the signal generator can produce
*/
enum emWaveFormType
{
	/** Undefined, here for consistency */
	emNULL = 0,
	ARBITRARY = 1,
	PWM = 2,
	SAW = 3,
	SINE = 4,
}

/**
Structure for an arbitrary wave form.
*/
struct emWaveForm
{
	/** Buffer holding the values */
	1: list<i32> Samples,	
	/** Playback rate */
	2: i32	Rate,
	/** Length of the buffer */
	3: i32 	SampleCount
}

/**
Definition of an operation to be performed. Not all parameters may be used. It will depend on the operationType. If something can't be performed by the EM, exceptions should be thrown when adding to the buffers. Timings are defined by FPGA ticks, which will need to be converted to Hz.
*/
struct emSequenceItem
{

	/** What the action is */
	1: emSequenceOperationType operationType, 
	
	/** Where the action is applied to, as a list of connections to the material */
	2: list<i32>	pin,
	
	/** Ticks it should start at */
	3: i64 startTime, 
	/** Ticks it should end at */
	4: i64 endTime,
	
	/** Definition of signal, if appropriate */
	5: i32 frequency = 0,  
	6: i32 phase = 0,	
	7: i32 cycleTime = 0,
	8: i32 amplitude = 0,
	//TODO: negative/positive amplitude?
	
	/** Predefined wave form, eg. square, sawtooth, sine */
	9: emWaveFormType waveFormType, 
	
	/** If playing back an arbitrary signal, buffer is attached here */
	10: emWaveForm waveForm,
	
	/** If >=0 wait for a trigger on certain pin before running */
	11: i32 waitForTrigger = -1, 
	
	/** if  CONSTANT_FROM_REGISTER, then use this value*/
	12: i32 ValueSourceRegister = -1, 
}

/**
Debug struct
*/
struct emDebugInfo
{
	1: binary stateBlob,
	2: i32	stateBlobLength,
	3: map<string,string> values
}


/**
This is the definition of the API. These methods are what external programs will see when they connect.

All operations# return error info by throwing exceptions. Exceptions therefore should be processed, and not ignored.
*/
service emEvolvableMotherboard
{
	/** Test for the existence of the service */
	i32	ping() throws (1:emException err), 
	
	/** Set LED status */
	void setLED(1:i32 index, 2:bool state) throws (1:emException err), 	
	
	/** Each motherboard should have an ID for logging, versioning etc */
	string	getMotherboardID() throws (1:emException err), 
		
	/** Description of what the EM is currently doing */
	string	getMotherboardState() throws (1:emException err), 
	
	/** See if there is any error message */
	string	getLastError() throws (1:emException err),
	
	/** Resets the motherboard to initial, default config. Returns true if OK */
	bool reset() throws (1:emException err), 
	
	/** Reprogramme the FPGA */	
	bool reprogramme(1:binary bin,2:i32 length) throws (1:emException err), 
	
	/** Get debug state */
	emDebugInfo getDebugState() throws (1:emException err), 
	
	/** Clears the sequence queue */
	void clearSequences() throws (1:emException err),
	
	/** Runs the queued sequences */
	void runSequences() throws (1:emException err), 
	
	/** Pauses the running of queued sequences */
	void stopSequences() throws (1:emException err), 
	
	/** Waits until the sequences have all completed */
	void joinSequences() throws (1:emException err), 

	/** Queues something for the EM to do */
	void appendSequenceAction(
		/** Item to be queued */
		1: emSequenceItem Item
		) throws (1:emException err), 
	
	/** Retrieves a sample buffer */
	emWaveForm getRecording(
		/** Gets the buffer that was recorded by srcPin */
		1:i32 srcPin
		)throws (1:emException err),
	
	/** Clears a sample buffer associated with a pin */
	void clearRecording(
		/** Recording pin */
		1:i32 srcPin
		) throws (1:emException err), 
	
	/** Gets the current FPGA core temperature */
	i32 getTemperature() throws (1:emException err), 
	
	/** Configures where this EM should log to */	
	void setLogServer(
		/** Definition of experiment name and log server */
		1:emLogServerSettings logServer
		)
	throws (1:emException err),
	
	/** Set config register */	
	void setConfigRegister(
		/** Index of register to apply to */
		1:i32 index,
		/** Value to apply */
		2:i32 value
		)
	throws (1:emException err),
}

/**
For efficiency, wave data is stored in a central repository. This means programs do not have to transfer the whole dataset around when working in a distributed fashion. 

Wavebuffers are stored in a hashtable, as a {string,emWaveForm} pair.

Client applications need to tidy up the hashtable by deleting waveforms that are no longer used.

*/
service emDataApi /*extends shared.SharedService */
{
	/** Make a buffer of a certain length, returns its name */
	string createBuffer(
		/** The length*/
		1:i64 length
		) throws (1:emException err), 
		
	/** Frees the named buffer */
	void destroyBuffer(
		/** The name of the buffer */
		1:string bufferName
		) throws (1:emException err),
	
	/** Get the actual raw data */
	emWaveForm getBuffer(
		/** The name of the buffer */
		1:string bufferName
		) throws (1:emException err), 
	
	
	/** Creates a buffer from an existing sample */
	string setBuffer(
		/** Existing source */
		1:emWaveForm samples) 
		throws (1:emException err), 
	
	
	/** Copies a buffer, returns the copies name */
	string cloneBuffer(
		1:string bufferName
		) throws (1:emException err), 
	
	/** Rename a buffer, can be used to make code human readable */
	void renameBuffer(
		/** The buffer to rename */
		1:string oldBufferName, 
		/** The new name */
		2:string newBufferName
		) throws (1:emException err), 
	
	/** Rename a buffer */
	void saveBuffer(
		/** The buffer name */
		1:string bufferName, 
		/** Where to save it to */
		2:string fileName
		) throws (1:emException err), 
	
	/** Load a buffer from disc. Returns the name of the new buffer */
	string loadBuffer(
		/** File containing the buffer */
		1:string fileName
		) throws (1:emException err), 
	
	/** Element wise, compute difference of two buffers. Returns name of the output buffer. */
	string absDifference(		
		1:string bufferA, 
		2:string bufferB
		) throws (1:emException err),
	
	/** Find the sum of the buffer */
	double sum(
		1:string bufferName
		) throws (1:emException err),
	
	/** Computes the sum squared difference of two buffers */
	double sumSquaredDifference(
		1:string bufferA,
		2:string bufferB
		) throws (1:emException err), 
	
	/** Element wise subtraction, e.g. bufferA[n] - bufferB[n]. Returns name of the output buffer. */
	string subtract(
		1:string bufferA, 
		2:string bufferB
		) throws (1:emException err), 
	
	/** Element wise addition, e.g. bufferA[n] +  bufferB[n]. Returns name of the output buffer. */
	string add(
		1:string bufferA, 
		2:string bufferB
		) throws (1:emException err), //Element wise addition
	
	/** Set all the values in the buffer to value */
	void setValues(
		/** Buffer name */
		1:string bufferName,
		/** New value */
		2:i32 value
		) throws (1:emException err), 
	
	
	/** if value<threshold, 0 else maxValue. invert does the opposite. Returns name of new buffer */
	string threshold(
		/** Buffer name */
		1:string bufferName, 
		/** Value to test against */
		2:i32 thresholdValue, 
		/** Value to set if condition is met */
		3:i32 maxValue, 
		/** Invert response */
		4:bool invert
		) throws (1:emException err),
	
	/** Normalize the data. Returns name of new buffer */
	string normalize(
		/** Buffer name */
		1:string bufferName, 
		/** Normalize from */
		2:i32 minValue, 
		/** Normalize to */
		3:i32 maxValue
		) throws (1:emException err), 
	
	/** Quantize the data to discrete levels. Returns name of new buffer */
	string quantize(
		/** Buffer name */
		1:string bufferName, 
		/** Normalize from */
		2:i32 minValue, 
		/** Normalize to */
		3:i32 maxValue, 
		/** How many discrete levels to quantize to */
		4:i32 levels
		) throws (1:emException err), 
	
	/** Resize the data buffer. Returns name of new buffer. */
	string resample(
		/** Buffer name */
		1:string bufferName, 
		/** Number of samples in returned buffer */
		2:i32 newLength
		) throws (1:emException err), 
	
	/** Applies the median filter for noise supression.  Returns name of new buffer. */
	string medianFilter(
		/** Buffer name */
		1:string bufferName, 
		/** Strength of the filter. Larger number is stronger. Must be >=1 */
		2:i32 fitlerSize
		) throws (1:emException err), 
}




/**

*/
service emLogServer /*extends shared.SharedService */
{
	/** Make a unique experiment name based on time, date, etc*/
	string createUniqueExperimentName(
		/** User defined text to be incorporated into the experiment name*/
		1:string baseName
		) throws (1:emException err),
	
	/** Returns a structure encapsulating the server config */
	emLogServerSettings getLogServerSettings(
		/** The experiment name*/
		1:string uniqueExperimentName
		) throws (1:emException err),
		
	void log(
		/** Server and experiment details*/
		1:emLogServerSettings logServer,
		/** Message to log */
		2:string message,
		/** What type of message is it */
		3:emLogEventType messageType
		) throws (1:emException err)
}