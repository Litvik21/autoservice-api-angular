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
