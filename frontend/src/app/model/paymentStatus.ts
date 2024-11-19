export enum PaymentStatus {
  Waiting = 'Waiting',
  Paid = 'Paid',
  Not_Paid = 'Not_Paid',
  Paid_Out = 'Paid_Out',
}

export const PaymentStatusMapping = {
  [PaymentStatus.Waiting]: 'Waiting',
  [PaymentStatus.Paid]: 'Paid',
  [PaymentStatus.Not_Paid]: 'Not_Paid',
  [PaymentStatus.Paid_Out]: 'Paid_Out'
}
