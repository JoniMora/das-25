export interface Promotion {
  id: string;
  name: string;
  brandName: string;
  imageUrl?: string;
  description?: string;
  type: 'PERCENT' | 'AMOUNT' | 'FIXED';
  amount: number;
  startDate: string; // ISO
  endDate: string;   // ISO
  restaurant: { id: string; name: string; locality?: string };
}

