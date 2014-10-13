package nascence.vm.thrift;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import emInterfaces.emSequenceItem;
import emInterfaces.emSequenceOperationType;
import emInterfaces.emWaveForm;
import emInterfaces.emWaveFormType;

public class Sequence {
	ArrayList<emSequenceItem> sequence;
	List<Integer> unusedInputPins; // = used output pins

	public Sequence() {
		sequence = new ArrayList<emSequenceItem>();
	}

	/**
	 * This is here for reporting the VM output as a sequence
	 * 
	 * @param array
	 * @param amplitude
	 */
	public Sequence(double[][] array, int amplitude, List<Integer> usedPins) {
		this.unusedInputPins = null; // should be a complement to usedPins
		setFromArray(array, amplitude, usedPins);
	}

	/**
	 * Adds an item and recalculates the unusedPins list, such that the VM knows
	 * which pins to use for input and output.
	 * @param item
	 */
	public void addSequenceItem(emSequenceItem item) {
		sequence.add(item);
		updateUnusedPins((int)maxPinNumber());
	}

	public List<Integer> getUnusedInputPins() {
		return unusedInputPins;
	}

	/*
	 * This function analyzes the sequences and returns a 2D matrix of values,
	 * that can be used as a sequence of VM inputs. It has to be well defined
	 * for all of the pins. Pins, that are not provided any value in the
	 * sequence are charged with 0 values by defaul.
	 */
	public double[][] getAsArray(int nPins) {
		int min = (int) minTime();
		int max = (int) maxTime();
		int length = max - min + 1;

		double[][] result = new double[length][nPins];

		// each item is mapped to the array, items can be overwritten

		for (emSequenceItem item : sequence) {
			double amplitude = (double) item.getAmplitude();
			int startTime = (int) item.getStartTime();
			List<Integer> pins = item.getPin();
			double v;

			int sampleIndex;
			switch (item.getOperationType()) {
			case ARBITRARY:
				List<Integer> samples = item.getWaveForm().getSamples();
				// there should be another check for the waveform types here
				sampleIndex = 0;
				for (Integer s : samples) {
					v = (double) s.intValue() / amplitude;
					for (int p : pins) {
						result[startTime + sampleIndex][p] = v;
					}
					sampleIndex++;
				}
				break;
			default:
				break;

			}
		}
		return result;
	}

	/*
	 * This function generates a sequence containing one item per pin that holds
	 * the waveforms constructed from the given array. It uses all the pins
	 */
	void setFromArray(double[][] array, int amplitude) {
		sequence = new ArrayList<emSequenceItem>();
		this.unusedInputPins = unusedPins(array);

		for (int pin = 0; pin < array[0].length; pin++) { // pin, column-wise
			emSequenceItem item = new emSequenceItem();
			item.setStartTime(0);
			item.setEndTime(array.length - 1);
			item.setOperationType(emSequenceOperationType.ARBITRARY);
			ArrayList<Integer> pins = new ArrayList<Integer>();
			pins.add(pin);
			item.setPin(pins); // each pin is a separate item
			item.setAmplitude(amplitude);

			emWaveForm w = new emWaveForm();
			w.setSampleCount(array.length);
			ArrayList<Integer> samples = new ArrayList<Integer>();

			for (int t = 0; t < array.length; t++) { // time
				samples.add((int) Math
						.round((double) amplitude * array[t][pin]));
			}
			w.setSamples(samples);

			item.setWaveForm(w);
			item.setWaveFormType(emWaveFormType.ARBITRARY);
			sequence.add(item);
		}

	}

	/**
	 * The same function, but generates the specific pins
	 * 
	 * @param array
	 * @param amplitude
	 * @param usedPins
	 */
	void setFromArray(double[][] array, int amplitude, List<Integer> usedPins) {
		sequence = new ArrayList<emSequenceItem>();

		for (int pin = 0; pin < array[0].length; pin++) { // pin, column-wise
			emSequenceItem item = new emSequenceItem();
			item.setStartTime(0);
			item.setEndTime(array.length - 1);
			item.setOperationType(emSequenceOperationType.ARBITRARY);
			ArrayList<Integer> pins = new ArrayList<Integer>();
			pins.add(usedPins.get(pin));
			item.setPin(pins); // each pin is a separate item
			item.setAmplitude(amplitude);

			emWaveForm w = new emWaveForm();
			w.setSampleCount(array.length);
			ArrayList<Integer> samples = new ArrayList<Integer>();

			for (int t = 0; t < array.length; t++) { // time
				samples.add((int) Math
						.round((double) amplitude * array[t][pin]));
			}
			w.setSamples(samples);

			item.setWaveForm(w);
			item.setWaveFormType(emWaveFormType.ARBITRARY);
			sequence.add(item);
		}

	}

	/*
	 * returns minimum of time ticks available in the sequence
	 */
	long minTime() {
		long minValue = Long.MAX_VALUE;
		for (emSequenceItem item : sequence) {
			if (item.startTime < minValue) {
				minValue = item.startTime;
			}
		}
		return minValue;
	}

	/*
	 * returns maximum of time ticks available in the sequence
	 */
	long maxTime() {
		long maxValue = Long.MIN_VALUE;
		for (emSequenceItem item : sequence) {
			if (item.endTime > maxValue) {
				maxValue = item.endTime;
			}
		}
		return maxValue;
	}
	
	long maxPinNumber() {
		long maxValue = Long.MIN_VALUE;
		for (emSequenceItem item : sequence) {
			for(int pin : item.getPin()){
				if(pin > maxValue){
					maxValue = pin;
				}
			}
		}
		return maxValue;
	}

	/*
	 * Finds out which pins are not assigned as inputs (zero-columns)
	 */
	public List<Integer> unusedPins(double[][] array) {
		ArrayList<Integer> pins = new ArrayList<Integer>();

		boolean zeroQ = true;
		for (int p = 0; p < array[0].length; p++) {
			zeroQ = true;
			for (int t = 0; t < array.length; t++) {
				if (array[t][p] != 0.0) {
					zeroQ = false;
				}
			}
			if (zeroQ) {
				pins.add(new Integer(p));
			}
		}
		return pins;
	}
	
	/**
	 * This examines the inputSequence and updates the list of unused pins.
	 */
	public void updateUnusedPins(int nPins){
		unusedInputPins = new ArrayList<Integer>();
		
		BitSet usedPins = new BitSet(nPins);
		for(emSequenceItem item : sequence){
			for(int pin : item.getPin()){
				usedPins.set(pin);
			}
		}
		for(int i=0;i<usedPins.size();i++){
			if(false==usedPins.get(i)){
				unusedInputPins.add(i);
			}
		}
	}

	/*
	 * This function returns recording for the given pin takes only the first
	 * item, since VM does not enter more than one waveform.
	 */
	public emWaveForm getRecording(int pin) {
		// VM stores the pins in order, it searches the first item
		// it does not store values for multiple pins, so the first 
		// pin is used
		int pinIndex =0;
		int nItems = sequence.size();
		while(pin!=(sequence.get(pinIndex)).getPin().get(0) && pinIndex < nItems){
			pinIndex++;
		}
		
		emSequenceItem item = sequence.get(pinIndex);
		return item.getWaveForm();

	}

	public static void main(String[] arg) {
		Sequence s = new Sequence();
		double[][] data = { { 0.1, 1.0 }, { 0.3333, 2.0 }, { 0.0, 1.0 } };

		s.setFromArray(data, 255);
		double[][] ar = s.getAsArray(2);

		for (int i = 0; i < ar.length; i++) {
			for (int j = 0; j < ar[0].length; j++) {
				System.out.print(ar[i][j] + " ");

			}
			System.out.println();
		}
	}
	public List<emSequenceItem> getThisSequence(){
		return sequence;
	}
}
