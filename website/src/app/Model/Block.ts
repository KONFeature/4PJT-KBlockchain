import { Transaction } from "./Transaction";

export class Block {
  id: number;
  prevHash: string;
  hash: string;
  transactions: Transaction[];
  nonce: number;
  timestamp: string;
  transactionCount: number;
}
