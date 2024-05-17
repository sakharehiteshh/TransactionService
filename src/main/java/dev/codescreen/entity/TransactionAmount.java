package dev.codescreen.entity;

public class TransactionAmount {
	
	private String amount;

	private String currency;

	private String debitOrCredit;

	public TransactionAmount() {
	}

	public TransactionAmount(String amount, String currency, String debitOrCredit) {
		this.amount = amount;
		this.currency = currency;
		this.debitOrCredit = debitOrCredit;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDebitOrCredit() {
		return debitOrCredit;
	}

	public void setDebitOrCredit(String debitOrCredit) {
		this.debitOrCredit = debitOrCredit;
	}
	
}
