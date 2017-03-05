package by.htp.belavia.entity;

public class PricesMatrix {
	private String outbandDate, inboundDate, price;
	//private double price;
	
	public PricesMatrix() {
		super();
	}

	public PricesMatrix(String price, String outbandDate, String inboundDate) {
		super();
		this.outbandDate = outbandDate;
		this.inboundDate = inboundDate;
		this.price = price;
	}

	public String getOutbandDate() {
		return outbandDate;
	}

	public void setOutbandDate(String outbandDate) {
		this.outbandDate = outbandDate;
	}

	public String getInboundDate() {
		return inboundDate;
	}

	public void setInboundDate(String inboundDate) {
		this.inboundDate = inboundDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inboundDate == null) ? 0 : inboundDate.hashCode());
		result = prime * result + ((outbandDate == null) ? 0 : outbandDate.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PricesMatrix other = (PricesMatrix) obj;
		if (inboundDate == null) {
			if (other.inboundDate != null)
				return false;
		} else if (!inboundDate.equals(other.inboundDate))
			return false;
		if (outbandDate == null) {
			if (other.outbandDate != null)
				return false;
		} else if (!outbandDate.equals(other.outbandDate))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PricesMatrix [price="+ price +", outbandDate=" + outbandDate + ", inboundDate=" + inboundDate + "]";
	}
	
}
