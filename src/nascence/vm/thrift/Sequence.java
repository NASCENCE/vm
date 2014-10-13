package nascence.vm.thrift;

import java.util.ArrayList;
import java.util.List;

import emInterfaces.emSequenceItem;
import emInterfaces.emSequenceOperationType;
import emInterfaces.emWaveForm;
import emInterfaces.emWaveFormType;

public class Sequence {
	ArrayList<emSequenceItem> sequence;

	public Sequence() {
		sequence = new ArrayList<emSequenceItem>();
	}

	public void addSequenceItem(emSequenceItem item) {
		sequence.add(item);
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
						result[startTime+sampleIndex][p] = v;
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
	 * the waveforms constructed from the given array.
	 */
	void setFromArray(double[][] array, int amplitude) {
		sequence = new ArrayList<emSequenceItem>();

		for (int pin = 0; pin < array[0].length; pin++) { // pin, column-wise
			emSequenceItem item = new emSequenceItem();
			item.setStartTime(0);
			item.setEndTime(array.length-1);
			item.setOperationType(emSequenceOperationType.ARBITRARY);
			ArrayList<Integer> pins = new ArrayList<Integer>();
			pins.add(pin);
			item.setPin(pins); // each pin is a separate item
			item.setAmplitude(amplitude);
			
			emWaveForm w = new emWaveForm();
			w.setSampleCount(array.length);
			ArrayList<Integer> samples = new ArrayList<Integer>();
			
			for (int t = 0; t < array.length; t++) { // time
				samples.add((int)Math.round((double)amplitude * array[t][pin]));
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

	public static void main(String[] arg){
		Sequence s = new Sequence();
		double[][] data = {{0.1,1.0},{0.3333,2.0},{0.0,1.0}};
		
		s.setFromArray(data,255);
		double[][] ar = s.getAsArray(2);
		
		for(int i=0;i<ar.length;i++){
			for(int j=0;j< ar[0].length;j++){
				System.out.print(ar[i][j]+" ");
				
			}
			System.out.println();
		}
	}
}
