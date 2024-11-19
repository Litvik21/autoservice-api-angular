export enum Status {
  Received = "Received",
  Process = "Process",
  Successfully_Completed = "Successfully_Completed",
  Not_Successfully_Completed = "Not_Successfully_Completed",
  Paid = "Paid",
}

export const StatusMapping = {
  [Status.Received]: "Received",
  [Status.Process]: "Process",
  [Status.Successfully_Completed]: "Successfully_Completed",
  [Status.Not_Successfully_Completed]: "Not_Successfully_Completed",
  [Status.Paid]: "Paid",
}

export function mapStringToStatus(statusString: string): Status | null {
  switch (statusString.toUpperCase()) {
    case 'RECEIVED':
      return Status.Received;
    case 'PROCESS':
      return Status.Process;
    case 'SUCCESSFULLY_COMPLETED':
      return Status.Successfully_Completed;
    case 'NOT_SUCCESSFULLY_COMPLETED':
      return Status.Not_Successfully_Completed;
    case 'PAID':
      return Status.Paid;
    default:
      return null; // Если статус не найден, возвращаем null или обрабатываем ошибку
  }
}
