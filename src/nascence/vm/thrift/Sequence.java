package nascence.vm.thrift;

import java.util.ArrayList;

import emInterfaces.emSequenceItem;

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
		long min = minTime();
		long max = maxTime();
		int length = (int) max - (int) min + 1;
		double[][] result = new double[length][nPins];

		for (int i = 0; i < length; i++) {
			
		}
		return result;
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
			if (item.startTime > maxValue) {
				maxValue = item.startTime;
			}
		}
		return maxValue;
	}
}
